package sqs

import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import com.amazonaws.services.sqs.AmazonSQSClientBuilder

fun main(){
    val con = URL("http://stream.meetup.com/2/open_events").openConnection() as HttpURLConnection
    con.requestMethod = "GET"
    val stream = BufferedReader(InputStreamReader(con.inputStream))
    while (stream.readLine() != null) {
        AmazonSQSClientBuilder
            .defaultClient()
            .sendMessage("https://sqs.eu-west-2.amazonaws.com/983527076849/meetup-open-events", stream.readLine())
    }
    stream.close()
}
