import com.example.authenticationservice.controller.AuthenticationController
import com.example.authenticationservice.exceptions.ParameterException
import com.example.authenticationservice.model.Prospect
import com.example.authenticationservice.parameters.RegisterUserRequest
import com.example.authenticationservice.parameters.PasswordResetRequest
import com.example.authenticationservice.parameters.SetPasswordRequest
import com.example.authenticationservice.sample.RegisterUserSample
import com.example.authenticationservice.service.AuthenticationService
import com.example.authenticationservice.service.EmailSenderService
import com.example.authenticationservice.service.ProspectService
import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.any
import org.mockito.Mockito.verify
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import javax.validation.Valid

@ExtendWith(SpringExtension::class)
@ActiveProfiles("test")
class AuthenticationControllerTest {

    private lateinit var authenticationController: AuthenticationController
    private val authenticationService = mockk<AuthenticationService>()
    private val emailSenderService = mockk<EmailSenderService>()
    private val prospectService = mockk<ProspectService>()

    @BeforeEach
    fun setUp() {
        authenticationController = AuthenticationController(authenticationService, emailSenderService, prospectService)
        unmockkAll()
    }

    @Test
    fun `registerUser should return 201 HttpStatus`() {
        val sample = RegisterUserSample.getRegisterUserSuccess()
        every { authenticationService.registerUser(sample) } returns String()

        val responseEntity: ResponseEntity<Void> = authenticationController.registerUser(sample)

        assert(responseEntity.statusCode == HttpStatus.CREATED)
        verify(exactly = 1) { authenticationService.registerUser(any()) }
    }
}
