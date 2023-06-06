import com.example.authenticationservice.controller.OrganizerController
import com.example.authenticationservice.dto.CreateEventDto
import com.example.authenticationservice.dto.EventJobDto
import com.example.authenticationservice.dto.MusicianEventJobDto
import com.example.authenticationservice.exceptions.ParameterException
import com.example.authenticationservice.parameters.CreateEventJobRequest
import com.example.authenticationservice.parameters.CreateEventRequest
import com.example.authenticationservice.parameters.DeleteEventRequest
import com.example.authenticationservice.parameters.FilterMusicianRequest
import com.example.authenticationservice.parameters.UpdateEventRequest
import com.example.authenticationservice.sample.RegisterEventSample
import com.example.authenticationservice.service.OrganizerService
import io.mockk.*
import org.hibernate.sql.Update
import org.hibernate.validator.internal.engine.ConstraintViolationImpl
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.springframework.data.domain.PageImpl
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import java.sql.Time
import java.time.LocalDate
import java.time.LocalTime
import javax.servlet.http.HttpServletRequest
import javax.validation.ConstraintViolationException
import javax.validation.Validator

class OrganizerControllerTest {
    private lateinit var organizerService: OrganizerService
    private lateinit var organizerController: OrganizerController

    @BeforeEach
    fun setup() {
        organizerService = mockk(relaxed = true)
        organizerController = OrganizerController(organizerService)
    }

    @Test
    fun `should create a event and return createEventDto`() {
        val request = mockk<HttpServletRequest>(relaxed = true)
        val createEventRequest = mockk<CreateEventRequest>(relaxed = true)

        val createEventDto = CreateEventDto()
        every { organizerService.createEvent(any(), any()) } returns createEventDto

        val response = organizerController.createEvent(request, createEventRequest)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(createEventDto, response.body)
        verify (exactly = 1) {  organizerService.createEvent(any(), any()) }
    }

    @Test
    fun `should create a eventJob and return eventJobDto`() {
        val request: HttpServletRequest = mockk<HttpServletRequest>(relaxed = true)
        val createEventJobRequest = mockk<CreateEventJobRequest>(relaxed = true)

        every { organizerService.createEventJob(any(), any()) } returns listOf<EventJobDto>()

        val response = organizerController.createEventJob(request, createEventJobRequest)
        val eventJobDto: List<EventJobDto> = listOf<EventJobDto>()

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(eventJobDto, response.body)
        verify (exactly = 1) { organizerService.createEventJob(any(), any()) }
    }

    @Test
    fun `should update event and return createEventDto`() {
        val request: HttpServletRequest = mockk<HttpServletRequest>(relaxed = true)
        val createEventJobRequest = mockk<UpdateEventRequest>(relaxed = true)
        val createEventDto = CreateEventDto()

        every { organizerService.updateEvent(any(), any()) } returns createEventDto

        val response = organizerController.updateEvent(request, createEventJobRequest)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(createEventDto, response.body)
        verify (exactly = 1) { organizerService.updateEvent(any(), any()) }
    }


    @Test
    fun `should delete event`() {
        val request: HttpServletRequest = mockk<HttpServletRequest>(relaxed = true)
        val deleteEventJobRequest = mockk<DeleteEventRequest>(relaxed = true)

        every { organizerService.deleteEvent(any(), any()) } returns Unit

        val response = organizerController.deleteEvent(request, deleteEventJobRequest)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(null, response.body)

        verify (exactly = 1) { organizerService.deleteEvent(any(), any()) }
    }

    @Test
    fun `should delete event job`() {
        val request: HttpServletRequest = mockk<HttpServletRequest>(relaxed = true)
        val eventJobId: Long = 1

        every { organizerService.deleteEventJob(any(), any()) } returns Unit

        val response = organizerController.deleteEventJob(request, eventJobId)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(null, response.body)

        verify (exactly = 1) { organizerService.deleteEventJob(any(), any()) }
    }
}