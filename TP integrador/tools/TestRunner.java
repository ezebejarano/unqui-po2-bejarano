import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;
import static org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.request;

import java.io.PrintWriter;

import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;

/**
 * Runner por consola para los tests JUnit 5 del dominio.
 * Descubre y ejecuta todos los tests del paquete base ar.edu.unq.po2.unqshop.
 */
public class TestRunner {

	public static void main(String[] args) {
		String paquete = "ar.edu.unq.po2.unqshop";
		if (args.length > 0) {
			paquete = args[0];
		}

		LauncherDiscoveryRequest peticion = request()
				.selectors(selectPackage(paquete))
				.build();

		Launcher launcher = LauncherFactory.create();
		SummaryGeneratingListener listener = new SummaryGeneratingListener();
		launcher.registerTestExecutionListeners(listener);
		launcher.execute(peticion);

		TestExecutionSummary resumen = listener.getSummary();
		PrintWriter salida = new PrintWriter(System.out);
		resumen.printTo(salida);
		resumen.printFailuresTo(salida);
		salida.flush();

		if (resumen.getTotalFailureCount() > 0) {
			System.exit(1);
		}
	}
}
