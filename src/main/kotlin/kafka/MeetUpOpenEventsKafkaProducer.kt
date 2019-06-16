package kafka

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

fun main(){
    val con = URL("http://stream.meetup.com/2/open_events").openConnection() as HttpURLConnection
    con.requestMethod = "GET"
    val stream = BufferedReader(InputStreamReader(con.inputStream))
    while (stream.readLine() != null) {

    }
    stream.close()
}