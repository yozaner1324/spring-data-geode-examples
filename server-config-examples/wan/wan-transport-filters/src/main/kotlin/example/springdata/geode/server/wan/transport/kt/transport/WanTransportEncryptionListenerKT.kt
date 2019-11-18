package example.springdata.geode.server.wan.transport.kt.transport

import org.apache.geode.cache.wan.GatewayTransportFilter
import org.springframework.stereotype.Component

import java.io.InputStream
import java.io.OutputStream
import java.util.zip.Adler32
import java.util.zip.CheckedInputStream
import java.util.zip.CheckedOutputStream

@Component
class WanTransportEncryptionListenerKT : GatewayTransportFilter {

    private val checksum = Adler32()

    override fun getInputStream(stream: InputStream) = CheckedInputStream(stream, checksum)

    override fun getOutputStream(stream: OutputStream)= CheckedOutputStream(stream, checksum)
}