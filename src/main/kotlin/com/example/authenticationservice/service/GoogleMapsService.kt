import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.boot.web.client.RestTemplateBuilder

@Service
class GoogleMapsService(restTemplateBuilder: RestTemplateBuilder) {

    private val restTemplate: RestTemplate = restTemplateBuilder.build()

    @Value("\${google.maps.apiKey}")
    private lateinit var apiKey: String

    fun getDistanceMatrix(origins: String, destinations: String): String {
        val url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=$origins&destinations=$destinations&key=$apiKey"
        return restTemplate.getForObject(url, String::class.java)!!
    }
}
