package com.meuticket.pos.core.payment

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import br.com.setis.safra.integracaosafra.Gerenciador
import br.com.setis.safra.integracaosafra.listeners.TransacaoListenerCallback
import br.com.setis.safra.integracaosafra.entidades.SaidaTransacao
import android.widget.LinearLayout
import com.meuticket.pos.R
import br.com.setis.safra.integracaosafra.util.ReturnCodes
import br.com.setis.safra.integracaosafra.entidades.Operacoes
import android.os.Bundle
import br.com.setis.safra.integracaosafra.entidades.DadosAutomacao
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.AdapterView
import android.graphics.BitmapFactory
import android.util.DisplayMetrics
import br.com.setis.safra.integracaosafra.entidades.RequestApi
import br.com.setis.safra.integracaosafra.entidades.EntradaTransacao
import br.com.setis.safra.integracaosafra.printer.PrinterTextAlignment
import br.com.setis.safra.integracaosafra.printer.PrinterTextSize
import br.com.setis.safra.integracaosafra.printer.PrinterTextStyle
import android.graphics.Typeface
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import br.com.setis.safra.integracaosafra.listeners.PrinterListener
import br.com.setis.safra.integracaosafra.printer.PrinterStatus
import android.widget.Toast
import br.com.setis.safra.integracaosafra.BuildConfig
import br.com.setis.safra.integracaosafra.entidades.MifareBlockData
import br.com.setis.safra.integracaosafra.entidades.NFCRequest
import br.com.setis.safra.integracaosafra.listeners.MifareNFCCallback
import java.lang.Exception
import java.util.ArrayList
import java.util.concurrent.atomic.AtomicInteger

class PaymentActivity : AppCompatActivity() {
    private var gerenciadorIntegracaoSafra: Gerenciador? = null
    private val callbackTransacaoIntegracao: TransacaoListenerCallback =
        object : TransacaoListenerCallback {
            override fun transacaoFinalizada(saidaTransacao: SaidaTransacao) {
                Log.d(TAG, "processaSaidaTransacao! callback recebido")
                val builder = AlertDialog.Builder(this@PaymentActivity)
                val dialogLayout =
                    layoutInflater.inflate(R.layout.dialog_transacao, null) as LinearLayout
                val comprovante = dialogLayout.findViewById<View>(R.id.comprovante) as TextView

                //1 = aprovada // 3 = estornada
                if (saidaTransacao.obtemResultadoTransacao() == ReturnCodes.RESULT_APROVADA
                    || (saidaTransacao.obtemOperacaoRealizada() == Operacoes.CANCELAMENTO
                            || saidaTransacao.obtemOperacaoRealizada() == Operacoes.CANCELAMENTO_VIA_NSU
                            && saidaTransacao.obtemResultadoTransacao() == ReturnCodes.RESULT_ESTORNADA)
                ) {
                    comprovante.text = saidaTransacao.obtemComprovanteViaLojista().toString()
                    builder.setView(dialogLayout)
                } else if (saidaTransacao.obtemOperacaoRealizada() == Operacoes.REIMPRESSAO
                    && saidaTransacao.obtemResultadoTransacao() == ReturnCodes.RESULT_OK
                ) {
                    builder.setMessage("Sucesso " + saidaTransacao.obtemOperacaoRealizada().name)
                } else {
                    builder.setMessage("erro ao realizar " + saidaTransacao.obtemOperacaoRealizada().name)
                }
                val dialogComprovante: Dialog = builder.create()
                dialogComprovante.show()
            }

            override fun transacaoException(e: Exception) {
                Log.e(TAG, "transacaoException: ", e)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_safra_payment)
        val dadosAutomacao = DadosAutomacao("Empresa", "Automacao", "1.0")
        gerenciadorIntegracaoSafra = Gerenciador(this, dadosAutomacao)

        //parametro true = solicita que o aviso seja exibido, false = cancela a solicitacao (nao exibe aviso)
        //gerenciador.requestExclusiveSale(true);
        val tVversion = findViewById<TextView>(R.id.tv_version)
        tVversion.text = "safra v${BuildConfig.VERSION_NAME}"
        val btnExecutar = findViewById<Button>(R.id.executar)
        val spinnerOperacao = findViewById<Spinner>(R.id.spinner_operacao)
        val selectedPosition = AtomicInteger(-1)
        val operacoes: MutableList<String> = ArrayList()
        operacoes.add("Venda crédito")
        operacoes.add("Venda QR Code")
        operacoes.add("Venda débito")
        operacoes.add("Venda Voucher")
        operacoes.add("Venda Pré-Autorização")
        operacoes.add("Cancelamento de venda")
        operacoes.add("Reimpressão de comprovantes")
        operacoes.add("Devolução PIX")
        operacoes.add("Impressão livre")
        operacoes.add("Retentar impressão após erro")
        operacoes.add("Ler cartão NFC")
        operacoes.add("Escrever no cartão NFC")
        operacoes.add("Limpar Buffer Impressão")
        val adapter = ArrayAdapter(this, R.layout.element_operacao, R.id.tv_item, operacoes)
        spinnerOperacao.adapter = adapter
        spinnerOperacao.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
                btnExecutar.isEnabled = true
                selectedPosition.set(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                btnExecutar.isEnabled = false
            }
        }
        btnExecutar.setOnClickListener { v: View? ->
            when (selectedPosition.get()) {
                0 -> vendaCredito()
                1 -> vendaQrcode()
                2 -> vendaDebito()
                3 -> vendaVoucher()
                4 -> vendaPreAuto()
                5 -> cancelamento()
                6 -> reimpressaoComprovantes()
                7 -> devolucaoPix()
                8 -> impressaoLivre()
                9 -> retentarImpressaoAposErro()
                10 -> lerNFC()
                11 -> escreverNFC()
                12 -> limparBufferImpressao()
            }
        }
    }

    //redimensiona a densidade para a imagem aparecer mais compacta
    private val logoBitmap: Bitmap
        private get() {
            val options = BitmapFactory.Options()
            options.inDensity =
                DisplayMetrics.DENSITY_XXHIGH //redimensiona a densidade para a imagem aparecer mais compacta
            return BitmapFactory.decodeResource(
                resources,
                R.drawable.ic_launcher_background,
                options
            )
        }

    private fun enviaTransacao(requestApi: RequestApi) {
        gerenciadorIntegracaoSafra!!.realizaTransacao(requestApi, callbackTransacaoIntegracao)
    }

    private fun vendaCredito() {
        val entradaTransacao = EntradaTransacao(100, Operacoes.VENDA_CREDITO, "01222")
        val requestApi = RequestApi(entradaTransacao)
        enviaTransacao(requestApi)
    }

    private fun vendaQrcode() {
        val entradaTransacao = EntradaTransacao(500, Operacoes.VENDA_QRCODE, "011111")
        val requestApi = RequestApi(entradaTransacao)
        enviaTransacao(requestApi)
    }

    private fun vendaDebito() {
        val entradaTransacao = EntradaTransacao(100, Operacoes.VENDA_DEBITO, "0222222")
        val requestApi = RequestApi(entradaTransacao)
        enviaTransacao(requestApi)
    }

    private fun vendaVoucher() {
        val entradaTransacao = EntradaTransacao(4000, Operacoes.VENDA_VOUCHER, "12345")
        val requestApi = RequestApi(entradaTransacao)
        enviaTransacao(requestApi)
    }

    private fun vendaPreAuto() {
        val entradaTransacao = EntradaTransacao(100, Operacoes.PRE_AUTORIZACAO, "123456")
        val requestApi = RequestApi(entradaTransacao)
        enviaTransacao(requestApi)
    }

    private fun reimpressaoComprovantes() {
        val entradaTransacao = EntradaTransacao(Operacoes.REIMPRESSAO, "teste")
        val requestApi = RequestApi(entradaTransacao)
        enviaTransacao(requestApi)
    }

    private fun cancelamento() {
        val entradaTransacao = EntradaTransacao(Operacoes.CANCELAMENTO, "01")
        //exemplo de entrada para cancelamento via nsu:
//                EntradaTransacao entradaTransacao = new EntradaTransacao("020005179576", Operacoes.CANCELAMENTO_VIA_NSU, "01");
        val requestApi = RequestApi(entradaTransacao)
        enviaTransacao(requestApi)
    }

    private fun devolucaoPix() {
        val entradaTransacao = EntradaTransacao(Operacoes.DEVOLUCAO_PIX, "teste")
        val requestApi = RequestApi(entradaTransacao)
        enviaTransacao(requestApi)
    }

    private fun impressaoLivre() {
        val printerIntegracao = gerenciadorIntegracaoSafra!!.printerIntegracao
        val bitmap = logoBitmap
        printerIntegracao.addBitmap(bitmap, PrinterTextAlignment.LEFT)
        printerIntegracao.addBitmap(bitmap, PrinterTextAlignment.CENTER)
        printerIntegracao.addBitmap(bitmap, PrinterTextAlignment.RIGHT)
        printerIntegracao.addStringLine(
            "EXTRA_SMALL_NORMAL_12345678901234567890123456789",
            PrinterTextSize.EXTRA_SMALL,
            PrinterTextStyle.NORMAL,
            PrinterTextAlignment.CENTER,
            false
        )
        printerIntegracao.addStringLine(
            "EXTRA_SMALL_BOLD_1234567890123456789012345678901",
            PrinterTextSize.EXTRA_SMALL,
            PrinterTextStyle.BOLD,
            PrinterTextAlignment.CENTER,
            false
        )
        printerIntegracao.addStringLine(
            "SMALL_NORMAL_12345678901234567890123456789",
            PrinterTextSize.SMALL,
            PrinterTextStyle.NORMAL,
            PrinterTextAlignment.CENTER,
            false
        )
        printerIntegracao.addStringLine(
            "SMALL_BOLD_1234567890123456789012345678901",
            PrinterTextSize.SMALL,
            PrinterTextStyle.BOLD,
            PrinterTextAlignment.CENTER,
            false
        )
        printerIntegracao.addStringLine(
            "MEDIUM_NORMAL_123456789012345678901234",
            PrinterTextSize.MEDIUM,
            PrinterTextStyle.NORMAL,
            PrinterTextAlignment.CENTER,
            false
        )
        printerIntegracao.addStringLine(
            "MEDIUM_BOLD_12345678901234567890123456",
            PrinterTextSize.MEDIUM,
            PrinterTextStyle.BOLD,
            PrinterTextAlignment.CENTER,
            false
        )
        printerIntegracao.addStringLine(
            "LARGE_NORMAL_1234567890123456789",
            PrinterTextSize.LARGE,
            PrinterTextStyle.NORMAL,
            PrinterTextAlignment.CENTER,
            false
        )
        printerIntegracao.addStringLine(
            "LARGE_BOLD_123456789012345678901",
            PrinterTextSize.LARGE,
            PrinterTextStyle.BOLD,
            PrinterTextAlignment.CENTER,
            false
        )
        printerIntegracao.addStringLine(
            "EXTRALARGE_NORMAL_12",
            PrinterTextSize.EXTRA_LARGE,
            PrinterTextStyle.NORMAL,
            PrinterTextAlignment.CENTER,
            false
        )
        printerIntegracao.addStringLine(
            "EXTRALARGE_BOLD_1234",
            PrinterTextSize.EXTRA_LARGE,
            PrinterTextStyle.BOLD,
            PrinterTextAlignment.CENTER,
            false
        )
        printerIntegracao.addStringLine(
            "MEDIUM_INVERTED",
            PrinterTextSize.MEDIUM,
            PrinterTextStyle.NORMAL,
            PrinterTextAlignment.CENTER,
            true
        )

        //Cria a fonte customizada. Trocar para qualquer .ttf que o usuário desejar
        val customTypeface =
            Typeface.createFromAsset(this@PaymentActivity.assets, "MajorMonoDisplay-Regular.ttf")
        printerIntegracao.addStringLine(
            "FONTE PERSONALIZADA",
            customTypeface,
            32f,
            PrinterTextAlignment.CENTER,
            false
        )
        printerIntegracao.print(object : PrinterListener {
            override fun eventoSaida(printerStatus: PrinterStatus) {
                Log.d(TAG, "Evento saida printer: $printerStatus")
                if (printerStatus == PrinterStatus.ERROR_NO_PAPER) {
                    //tratar => PrinterStatus.ERROR_NO_PAPER
                    //exibir mensagem de erro sem papel, e quando necessario chamar printerIntegracao.print novamente.
                    // Não é necessario addLine novamente, pois o comprovante atual está no buffer, não é necessário criá-lo novamente
                    Toast.makeText(
                        this@PaymentActivity,
                        "Sem papel na impressora!\n Chamar 'reimprimir após erro'",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun eventoException(e: Exception) {
                Log.e(TAG, "Exception ", e)
            }
        }, 150)
    }

    private fun retentarImpressaoAposErro() {
        val printerIntegracao = gerenciadorIntegracaoSafra!!.printerIntegracao
        printerIntegracao.print(object : PrinterListener {
            override fun eventoSaida(printerStatus: PrinterStatus) {
                Log.d(TAG, "Evento saida printer: $printerStatus")
            }

            override fun eventoException(e: Exception) {
                Log.e(TAG, "Exception ", e)
            }
        }, 150)
    }

    private fun lerNFC() {
        val listBlocks: MutableList<MifareBlockData> = ArrayList()
        for (i in 0..15) {
            listBlocks.add(MifareBlockData(i, Utils.hexStringToByteArray("FFFFFFFFFFFF"), null))
        }
        val nfcRequest = NFCRequest("Por favor aproxime o cartão no terminal", listBlocks)
        gerenciadorIntegracaoSafra!!.readMifareNFCBlocks(nfcRequest, object : MifareNFCCallback {
            override fun onSuccess(list: List<MifareBlockData>) {
                runOnUiThread {
                    Toast.makeText(
                        this@PaymentActivity,
                        "Cartão lido com sucesso",
                        Toast.LENGTH_LONG
                    ).show()
                }
                for (block in list) {
                    Log.d(
                        TAG,
                        String.format(
                            "NFC block : %02d - Data: %s",
                            block.blockNumber,
                            Utils.bytesToHex(block.data)
                        )
                    )
                }
            }

            override fun onError(i: Int) {
                runOnUiThread {
                    Toast.makeText(
                        this@PaymentActivity,
                        "Erro ao ler o cartão",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun escreverNFC() {
        val listBlocks: MutableList<MifareBlockData> = ArrayList()
        listBlocks.add(
            MifareBlockData(
                1,
                Utils.hexStringToByteArray("FFFFFFFFFFFF"),
                Utils.hexStringToByteArray("000000000000000CAFE0000000000000")
            )
        )
        val nfcRequest = NFCRequest("Por favor aproxime o cartão no terminal", listBlocks)
        gerenciadorIntegracaoSafra!!.writeMifareNFCBlocks(nfcRequest, object : MifareNFCCallback {
            override fun onSuccess(list: List<MifareBlockData>) {
                runOnUiThread {
                    Toast.makeText(
                        this@PaymentActivity,
                        "Cartão escrito com sucesso",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onError(i: Int) {
                runOnUiThread {
                    Toast.makeText(
                        this@PaymentActivity,
                        "Erro ao escrever no cartão",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    private fun limparBufferImpressao() {
        gerenciadorIntegracaoSafra!!.printerIntegracao.clearBuffer(object : PrinterListener {
            override fun eventoSaida(printerStatus: PrinterStatus) {
                if (printerStatus == PrinterStatus.OK) {
                    Toast.makeText(
                        this@PaymentActivity,
                        "Buffer impressão limpo",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun eventoException(e: Exception) {
                Log.e(TAG, "eventoException: ", e)
            }
        })
    }

    companion object {
        private val TAG = PaymentActivity::class.java.simpleName
    }
}