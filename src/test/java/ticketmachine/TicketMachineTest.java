package ticketmachine;

import org.junit.Before;
import org.junit.Test;

import java.util.PrimitiveIterator;

import static org.junit.Assert.*;

public class TicketMachineTest {
	private static final int PRICE = 50; // Une constante

	private TicketMachine machine; // l'objet à tester

	@Before
	public void setUp() {
		machine = new TicketMachine(PRICE); // On initialise l'objet à tester
	}

	@Test
	// On vérifie que le prix affiché correspond au paramètre passé lors de l'initialisation
	// S1 : le prix affiché correspond à l’initialisation
	public void priceIsCorrectlyInitialized() {
		// Paramètres : message si erreur, valeur attendue, valeur réelle
		assertEquals("Initialisation incorrecte du prix", PRICE, machine.getPrice());
	}

	@Test
	// S2 : la balance change quand on insère de l’argent
	public void insertMoneyChangesBalance() {
		machine.insertMoney(10);
		machine.insertMoney(20);
		assertEquals("La balance n'est pas correctement mise à jour", 10 + 20, machine.getBalance()); // Les montants ont été correctement additionnés               
	}

	@Test
	// S3 : le ticket n'est pas imprimé si le montant inséré est insuffisant
	public void ticketNotPrinted() {
		machine.insertMoney(PRICE - 1);
		assertFalse("Le montant inséré est suffisant", machine.printTicket());
	}

	@Test
	// S4 : le ticket est imprimé car le montant inséré est suffisant
	public void ticketPrinted() {
		machine.insertMoney(PRICE);
		assertTrue("Le montant inséré n'est pas suffisant", machine.printTicket());
	}

	@Test
	// S5 : la balance est décrémentée du prix du ticket quand on imprime un ticket
	public void balanceIsDecremented() {
		machine.insertMoney(PRICE + 1);
		machine.printTicket();
		assertEquals("La balance n'est pas décrémentée", PRICE + 1 - machine.getPrice(), machine.getBalance());
	}

	@Test
	// S6 : le montant collecté est mis à jour quand on imprime un ticket
	public void updatedTotal() {
		machine.insertMoney(PRICE);
		machine.insertMoney(PRICE);
		assertEquals("Le total a été mis à jour", 0, machine.getTotal());
		machine.printTicket();
		assertEquals("Le total n'a pas été mis à jour", PRICE*2, machine.getTotal());
	}

	@Test
	// S7 : refund() rend correctement la monnaie
	public void moneyCorrectlyRefunded() {
		machine.insertMoney(PRICE + 20);
		assertEquals(PRICE + 20, machine.getBalance());
		machine.printTicket();
		assertEquals(20, machine.getBalance());
		machine.refund();
		assertEquals(0, machine.getBalance());
	}

	@Test
	// S8 : refund() remet la balance à zéro
	public void balanceEqualsZero() {
		machine.insertMoney(70);
		machine.printTicket();
		assertEquals("La balance est remise à zéro", 70 - machine.getPrice(), machine.getBalance());
		machine.refund();
		assertEquals("La balance n'est pas remise à zéro", 0, machine.getBalance());
	}

	@Test
	// S9 : un montant négatif ne peut pas être inséré
	public void negativeAmount() {
		machine.insertMoney(-10);
		assertEquals(0, machine.getBalance());
	}

	@Test
	// S10 : on ne peut pas créer de machine qui délivre des tickets dont le prix est négatif
	public void machineNotCreated() {
		try {
			TicketMachine machine2 = new TicketMachine(-10);
			fail("La machine a acccepté un prix négatif");
		}
		catch (IllegalArgumentException e){}
	}
}
