package cn.body.pressup.utils

import android.content.Context
import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.Locale

class Utils {

    companion object {

        fun log(msg: String?) {
            if (!msg.isNullOrEmpty()) {
                Log.d("LogHelper", msg)
            }
        }

        fun formatDatetime(datetime: Long): String {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)
            return simpleDateFormat.format(datetime)
        }


        /**
         * 从assets读取文本内容
         *
         * @param context
         * @param fileName 资源文件名
         * @return
         */
        fun readFileFromAssets(context: Context, fileName: String): String {
            val result = StringBuffer()
            var inputReader: InputStreamReader? = null
            var bufReader: BufferedReader? = null
            try {
                inputReader = InputStreamReader(
                    context.resources.assets.open(fileName),
                    StandardCharsets.UTF_8
                )
                bufReader = BufferedReader(inputReader)
                var lineTxt: String? = null
                while (bufReader.readLine().also { lineTxt = it } != null) {
                    result.append(lineTxt)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    inputReader?.close()
                    bufReader?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return result.toString().trim { it <= ' ' }
        }
    }
}