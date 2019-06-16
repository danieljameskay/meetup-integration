package rabbitmq

import com.rabbitmq.client.AMQP
import com.rabbitmq.client.ConnectionFactory
import org.apache.http.entity.ContentType
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.time.Instant
import java.util.*

const val queue_name = "open_events"

fun main() {

    ConnectionFactory().apply {
        host = "localhost"
        port = 5672
        username = "rabbitmq"
        password = "rabbitmq"
    }.newConnection().use { connection ->
        connection.createChannel().use { channel ->
            channel.queueDeclare(queue_name, false, false, false, mapOf())
            val con = URL("http://stream.meetup.com/2/open_events").openConnection() as HttpURLConnection
            con.requestMethod = "GET"
            val stream = BufferedReader(InputStreamReader(con.inputStream))
            while (stream.readLine() != null) {
                channel.basicPublish(
                    "",
                    queue_name,
                    null,
                    stream.readLine().toByteArray()
                )
                System.out.println("[x] Sent")
            }
        }
    }
}

//AMQP.BasicProperties().builder()
//.appId("open_events")
//.contentEncoding("UTF-8")
//.contentType("application/json")
//.correlationId("123")
//.deliveryMode(2)
//.expiration("60000")
//.headers(mapOf())
//.messageId("123")
//.priority(1)
//.replyTo("n/a")
//.timestamp(Date.from(Instant.EPOCH))
//.type("event")
//.userId("rabbitmq")
//.build()