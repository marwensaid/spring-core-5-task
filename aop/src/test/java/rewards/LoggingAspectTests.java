package rewards;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import rewards.internal.account.AccountRepository;

@RunWith(JUnitPlatform.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { SystemTestConfig.class })
public class LoggingAspectTests {

	// Convenient class from Spring Boot that captures console output
	@Rule
	public OutputCapture capture = new OutputCapture();

	@Autowired
	AccountRepository repository;

	@Test
	public void testLogger() {
		repository.findByCreditCard("1234123412341234");

		if (TestConstants.CHECK_CONSOLE_OUTPUT) {
			// AOP VERIFICATION
			// LoggingAspect should have output an INFO message to console

			String consoleOutput = capture.toString();
			assertTrue(consoleOutput.startsWith("INFO"));
			assertTrue(consoleOutput.contains("rewards.internal.aspects.LoggingAspect"));
		}
	}
}
