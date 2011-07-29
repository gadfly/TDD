package edu.luc.clearing;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CheckParserTest {
	private CheckParser parser;
	
	@Before
	public void setup(){
		parser = new CheckParser();
	}

	@Test
	public void shouldIgnoreCase() throws Exception {
		assertEquals(100, parser.parseExpression("one").intValue());
		assertEquals(200, parser.parseExpression("TWO").intValue());
		assertEquals(300, parser.parseExpression("Three").intValue());
		assertEquals(1000, parser.parseExpression("TEN").intValue());
		assertEquals(5000, parser.parseExpression("fifty").intValue());
		assertEquals(1000, parser.parseExpression("10").intValue());
		assertEquals(9900, parser.parseExpression("99").intValue());
		assertEquals(8700, parser.parseExpression("8 7 dollars").intValue());
		assertEquals(0, parser.parseExpression(" no dollars ").intValue());
		assertEquals(0, parser.parseExpression(" without dollars ").intValue());
		assertEquals(0, parser.parseExpression(" without cents ").intValue());
		//assertEquals(1, parser.parseExpression("penny").intValue());
		//assertEquals(5, parser.parseExpression("nickel").intValue());
		//assertEquals(25, parser.parseExpression("Quarter").intValue());
		//assertEquals(50, parser.parseExpression("Two Quarter").intValue());
		
	}

	@Test
	public void shouldHandleZeroAmount() throws Exception {
		assertEquals(0, parser.parseExpression(" no cents ").intValue());
		assertEquals(0, parser.parseExpression("zero").intValue());
		assertEquals(0, parser.parseExpression("zero and ZERO").intValue());
		assertEquals(0, parser.parseExpression("ZERO zero ").intValue());
		assertEquals(0, parser.parseExpression("$no ").intValue());
		assertEquals(0, parser.parseExpression("na cents ").intValue());
		assertEquals(9000, parser.parseExpression("ninety zero").intValue());
		assertEquals(7000, parser.parseExpression("seventy - zero").intValue());
	}

	@Test 
	public void shouldReturnNullFoInvalidString() throws Exception{
		assertEquals(null, parser.parseExpression("red"));
		assertEquals(null, parser.parseExpression(""));
		assertEquals(null, parser.parseExpression("-"));
		assertEquals(null, parser.parseExpression("|"));
		assertEquals(null, parser.parseExpression("[]"));
		assertEquals(null, parser.parseExpression("[-]"));
		assertEquals(null, parser.parseExpression("[*]"));
		assertEquals(null, parser.parseExpression(" four-teen "));
		assertEquals(null, parser.parseExpression(" fi/ve "));
		assertEquals(null, parser.parseExpression(" thir*teen "));
		assertEquals(null, parser.parseExpression(" SEVENTY-THREE D-O-L-L-A-RS"));
		assertEquals(null, parser.parseExpression(" SEVENTY-THREE DO L LAR S"));
		assertEquals(null, parser.parseExpression("six a n d 81/100"));
	}
	
	@Test
	public void shouldIgnoreSpace() throws Exception {
		assertEquals(800, parser.parseExpression(" eight ").intValue());
		assertEquals(400, parser.parseExpression("Four ").intValue());
		assertEquals(1400, parser.parseExpression(" fourteen ").intValue());
	}
	
	
	@Test
	public void shouldMatchWithDollars() throws Exception {
		assertEquals(100, parser.parseExpression("One dollar").intValue());
		assertEquals(200, parser.parseExpression("two dollars").intValue());
		assertEquals(300, parser.parseExpression("three  dollars").intValue());
		assertEquals(8000, parser.parseExpression(" Eighty  dollars  ").intValue());
		assertEquals(7600, parser.parseExpression(" SEVENTY SIX  dollars  ").intValue());
		assertEquals(1000, parser.parseExpression(" ten - dollar ").intValue());
		assertEquals(9500, parser.parseExpression(" ninety-five - dollar ").intValue());
		assertEquals(10000, parser.parseExpression(" 100 dollars ").intValue());
		assertEquals(700, parser.parseExpression(" 7 dollars ").intValue());
	}
	
	@Test
	public void shouldReturnNullOnlyDollar() throws Exception{
		assertEquals(null, parser.parseExpression("usd"));
		assertEquals(null, parser.parseExpression("USD"));
		assertEquals(null, parser.parseExpression("$"));
		assertEquals(null, parser.parseExpression("dollar"));
		assertEquals(null, parser.parseExpression("dollars"));
		assertEquals(null, parser.parseExpression("+"));
		assertEquals(null, parser.parseExpression("&"));
		assertEquals(null, parser.parseExpression("."));
		assertEquals(null, parser.parseExpression("-"));
		assertEquals(null, parser.parseExpression("/"));
		assertEquals(null, parser.parseExpression("\\"));
		assertEquals(null, parser.parseExpression("1*7 dollars"));
		assertEquals(null, parser.parseExpression("1/9 dollars"));
	}
	
	@Test
	public void shouldMatchWithTwoDigits() throws Exception{
		assertEquals(2600, parser.parseExpression("20 six").intValue());
		assertEquals(2100, parser.parseExpression("twenty one").intValue());
		assertEquals(7200, parser.parseExpression("seventy two").intValue());
		assertEquals(8700, parser.parseExpression("eighty 7").intValue());
		assertEquals(5300, parser.parseExpression("  fifty   three ").intValue());
		assertEquals(1300, parser.parseExpression("thirteen").intValue());
		assertEquals(5000, parser.parseExpression("fifty").intValue());
		assertEquals(8000, parser.parseExpression("eighty").intValue());
		assertEquals(7400, parser.parseExpression("seventy four").intValue());
		assertEquals(6900, parser.parseExpression("sixty nine").intValue());
		assertEquals(4700, parser.parseExpression(" forty  SEVEN  ").intValue());
		assertEquals(4700, parser.parseExpression(" forty  SEVEN  dollar").intValue());
		assertEquals(9100, parser.parseExpression(" NINeTY-one").intValue());
		assertEquals(9900, parser.parseExpression(" NINeTY-nine dollars").intValue());
		assertEquals(7300, parser.parseExpression(" SEVENTY-THREE DOLLARS and").intValue());
		assertEquals(7302, parser.parseExpression(" SEVENTY-THREE DOLLARS and two").intValue());
		assertEquals(7301, parser.parseExpression(" SEVENTY-THREE DOLLARS and one cent").intValue());
		assertEquals(7400, parser.parseExpression(" SEVENTY-THREE DOLLARS and 100 cents ").intValue());
		assertEquals(3005, parser.parseExpression(" thirty DOLLARS and five").intValue());
		assertEquals(3700, parser.parseExpression(" thirty DOLLARS and seven DOLLARS").intValue());
		assertEquals(6900, parser.parseExpression(" sixty DOLLARS and 9 DOLLARS").intValue());
		assertEquals(8000, parser.parseExpression(" seventy-one DOLLARS and 9 DOLLARS").intValue());
		assertEquals(4007, parser.parseExpression(" forty  and DOLLARS seven").intValue());
		assertEquals(8200, parser.parseExpression(" eighty - two").intValue());
		assertEquals(8300, parser.parseExpression(" eighty- three").intValue());
		assertEquals(8500, parser.parseExpression(" eighty-DOLLARS-five").intValue());
	}

	@Test
	public void shouldReturnNullIfTwoDigitsReverse() throws Exception{
		assertEquals(null, parser.parseExpression("one two three"));
		assertEquals(null, parser.parseExpression("ten ten"));
		assertEquals(null, parser.parseExpression("five twenty"));
		assertEquals(null, parser.parseExpression("seventy eighty"));
		assertEquals(null, parser.parseExpression("nine five"));
		assertEquals(null, parser.parseExpression("seven six"));
		assertEquals(null, parser.parseExpression("fourteen seven"));
		assertEquals(null, parser.parseExpression("nineteen one"));
		assertEquals(null, parser.parseExpression("one hundred"));
	}
	
	@Test
	public void shouldMatchOnlyCents()  throws Exception{
		assertEquals(87, parser.parseExpression("87/100").intValue());
		assertEquals(12, parser.parseExpression("12/   100").intValue());
		assertEquals(45, parser.parseExpression("45 /100").intValue());
		assertEquals(0, parser.parseExpression("0/100").intValue());
		assertEquals(1, parser.parseExpression("1\\100").intValue());
		assertEquals(76, parser.parseExpression("76 / 100").intValue());
		assertEquals(20, parser.parseExpression("20/ 100").intValue());
		assertEquals(94, parser.parseExpression("94 \\ 100").intValue());
		assertEquals(18, parser.parseExpression("18/100 dollars").intValue());
		assertEquals(39, parser.parseExpression("thirty-nine/100 dollars").intValue());
		assertEquals(5, parser.parseExpression("5/100 dollar").intValue());
		assertEquals(39, parser.parseExpression(" 39 / 1 0 0").intValue());
		assertEquals(0, parser.parseExpression("zero/100").intValue());
		
		assertEquals(30, parser.parseExpression("thirty/100").intValue());
		assertEquals(31, parser.parseExpression("thirty one/100").intValue());
		assertEquals(92, parser.parseExpression("ninety two/100").intValue());
		assertEquals(85, parser.parseExpression("eighty-five/100").intValue());
		assertEquals(73, parser.parseExpression("seventy-three/100 dollars").intValue());
		assertEquals(73, parser.parseExpression("seventy-three/100 dollar").intValue());
		assertEquals(7300, parser.parseExpression("seventy-three dollars/100").intValue());
		assertEquals(7300, parser.parseExpression("seventy-three dollars 0/100").intValue());
		assertEquals(7300, parser.parseExpression("seventy-three dollars zero cent").intValue());
		assertEquals(7300, parser.parseExpression("seventy-three dollars zero ").intValue());
		assertEquals(7300, parser.parseExpression("seventy-three dollars zero/100 ").intValue());
		assertEquals(7400, parser.parseExpression("seventy-three dollars 100/100 ").intValue());
		//cents
		assertEquals(23, parser.parseExpression("23 cents").intValue());
		assertEquals(1, parser.parseExpression("1 cent").intValue());
		assertEquals(0, parser.parseExpression("  0 cent  ").intValue());
		assertEquals(100, parser.parseExpression("  100 cent  ").intValue());
		assertEquals(7890, parser.parseExpression("  7890 cent  ").intValue());
		assertEquals(100000, parser.parseExpression("  100000 cents  ").intValue());
	}
	
	@Test
	public void shouReturnNullForInvalidCents() throws Exception {
		assertEquals(null, parser.parseExpression("  /100"));
		assertEquals(null, parser.parseExpression("/100"));
		assertEquals(null, parser.parseExpression("7.6/100"));
		assertEquals(null, parser.parseExpression("99.00/100"));
		assertEquals(null, parser.parseExpression("99/100.00"));
		assertEquals(null, parser.parseExpression("/"));
		assertEquals(null, parser.parseExpression("//"));
		assertEquals(null, parser.parseExpression("\\"));
		assertEquals(null, parser.parseExpression("/0"));
		assertEquals(null, parser.parseExpression("45a461/100/1b00"));
		assertEquals(null, parser.parseExpression("-1/100"));
		assertEquals(null, parser.parseExpression("5-/100"));
		assertEquals(null, parser.parseExpression("abcd/100"));
		assertEquals(null, parser.parseExpression("&/*100"));
		assertEquals(null, parser.parseExpression("43?/100"));		
		assertEquals(null, parser.parseExpression("(*)/(100)"));
		assertEquals(null, parser.parseExpression("(*)/(99)"));
		assertEquals(null, parser.parseExpression("100/99"));
		assertEquals(null, parser.parseExpression("100/101"));
		
		//cents
		assertEquals(null, parser.parseExpression("15/100 cents"));
	}
	
	@Test
	public void shouldMatchWithAndCents()  throws Exception{
		assertEquals(7317, parser.parseExpression(" SEVENTY-THREE DOLLARS and 17/100").intValue());
		assertEquals(0, parser.parseExpression("zero and 0/100").intValue());
		assertEquals(100, parser.parseExpression("zero and 100/100").intValue());
		assertEquals(699, parser.parseExpression("six and 99\\100").intValue());
		assertEquals(326, parser.parseExpression("three and 26 / 100 dollars").intValue());
		assertEquals(100, parser.parseExpression("100/100").intValue());
		assertEquals(100, parser.parseExpression("100/100 dollars").intValue());
		assertEquals(199, parser.parseExpression("one and 99/100 dollar ").intValue());
		assertEquals(200, parser.parseExpression("one and 100/100").intValue());
		assertEquals(250, parser.parseExpression("two and 50/100").intValue());
		assertEquals(284, parser.parseExpression("2 And 84 Cents").intValue());
		assertEquals(400, parser.parseExpression("4 and 0/100").intValue());
		assertEquals(5612, parser.parseExpression("50 six and 12/100").intValue());
		assertEquals(444, parser.parseExpression("four and44/100").intValue());
		assertEquals(1037, parser.parseExpression("ten and 37/100").intValue());
		assertEquals(900, parser.parseExpression("nine and 0/100").intValue());
		assertEquals(1159, parser.parseExpression("eleven and 59/100").intValue());
		assertEquals(1861, parser.parseExpression("eighteen dollars and 61/100").intValue());
		assertEquals(1782, parser.parseExpression("seventeenand82/100").intValue());
		assertEquals(9099, parser.parseExpression("ninety and 99/100").intValue());
		assertEquals(9999, parser.parseExpression("ninety nine anD 99/100").intValue());
		assertEquals(7343, parser.parseExpression(" SEVENTY-THREE DOLLAR AND 43 / 100").intValue());
		assertEquals(8000, parser.parseExpression(" SEVENTY-NINE DOLLARS AND 100 / 100").intValue());
		assertEquals(4700, parser.parseExpression(" fourty seven dollars And zero cents ").intValue());
		assertEquals(4700, parser.parseExpression(" fourty seven dollars aNd without cents ").intValue());
	}
	
	@Test 
	public void shouldMatchDollarsWithoutAnd() throws Exception{
		assertEquals(6924, parser.parseExpression(" sixty nine dollars twenty four cents").intValue());
		assertEquals(5900, parser.parseExpression(" 50 eight dollars 100/100 cents").intValue());
		assertEquals(2100, parser.parseExpression(" twenty one dollars 0 cents").intValue());
		assertEquals(7300, parser.parseExpression("seventy-three dollars 0/100").intValue());
		assertEquals(4700, parser.parseExpression(" fourty seven dollars 0 ").intValue());
		assertEquals(4700, parser.parseExpression(" fourty seven dollars no cents ").intValue());
		assertEquals(4700, parser.parseExpression(" fourty seven dollars zero cents ").intValue());
		assertEquals(4700, parser.parseExpression(" fourty seven dollars without cents ").intValue());
	}
	
	@Test
	public void shouldReturnNullWithoutAnd()  throws Exception{
		assertEquals(null, parser.parseExpression("thirty 99/100"));
		assertEquals(null, parser.parseExpression("$11  76cents"));
	}
	
	@Test
	public void shouldHandleMultiAnd() throws Exception{
		assertEquals(3754, parser.parseExpression(" thirty DOLLARS and seven DOLLARS and 54/100").intValue());
		assertEquals(3175, parser.parseExpression("thirty and one dollars and 75/100").intValue());
		assertEquals(5963, parser.parseExpression("50 and nine dollars + 63 cents").intValue());
		assertEquals(9372, parser.parseExpression("ninety and 3 dollars & 72/100 dollar").intValue());
		assertEquals(2999, parser.parseExpression("$20+ 9 & 99/100 ").intValue());
		assertEquals(4999, parser.parseExpression("$forty aNd 9 + 99 cents ").intValue());
	}
	
	@Test
	public void shouldReturNullMultiAnd() throws Exception{
		assertEquals(null, parser.parseExpression("thirty centsand99and/100"));
		assertEquals(null, parser.parseExpression("thirty and99and/100and"));
		assertEquals(null, parser.parseExpression("41 and 1 and + 99/100+"));
		assertEquals(null, parser.parseExpression("$79 & 1 and + 99/100+"));
		assertEquals(null, parser.parseExpression("$31 & 5 and & 99/100+"));
		assertEquals(null, parser.parseExpression("$12+ 9 & 99/100& "));
		assertEquals(null, parser.parseExpression("thirty and + 99/100"));
	}
	
	@Test
	public void shouldTreatPlusAsAnd() throws Exception{
		assertEquals(7917, parser.parseExpression(" SEVENTY-nine DOLLARS + 17/100").intValue());
		assertEquals(0, parser.parseExpression("zero + 0/100").intValue());
		assertEquals(100, parser.parseExpression("zero + 100/100").intValue());
		assertEquals(699, parser.parseExpression("six + 99\\100").intValue());
		assertEquals(326, parser.parseExpression("three + 26 / 100 dollars").intValue());
		assertEquals(100, parser.parseExpression("+100/100").intValue());
		assertEquals(2996, parser.parseExpression("Twenty Nine Dollar + 96 Cents").intValue());
		assertEquals(200, parser.parseExpression("one + 100/100").intValue());
		assertEquals(250, parser.parseExpression("two + 50 cent").intValue());
		assertEquals(1000, parser.parseExpression("ten + 0/100").intValue());
		assertEquals(5612, parser.parseExpression("50 six + 12/100 dollars").intValue());
		assertEquals(444, parser.parseExpression("four +44/100").intValue());
		assertEquals(1037, parser.parseExpression("ten + 37/100").intValue());
		assertEquals(900, parser.parseExpression("nine + 0/100").intValue());
		assertEquals(1159, parser.parseExpression("eleven + 59/100").intValue());
		assertEquals(1861, parser.parseExpression("eighteen dollars + 61/100").intValue());
		assertEquals(1782, parser.parseExpression("seventeen+82/100").intValue());
		assertEquals(9099, parser.parseExpression("ninety + 99/100 ").intValue());
		assertEquals(9999, parser.parseExpression("ninety nine + 99/100  ").intValue());
		assertEquals(7343, parser.parseExpression(" SEVENTY-THREE DOLLAR + 43 / 100").intValue());
		assertEquals(8000, parser.parseExpression(" SEVENTY-NINE DOLLARS + 100 / 100").intValue());
		assertEquals(4700, parser.parseExpression(" fourty seven dollars + zero cents ").intValue());
		assertEquals(6700, parser.parseExpression(" sixty seven dollars + without cents ").intValue());
	}
	
	@Test
	public void shouldTreatAmpConnectAsAnd() throws Exception{
		assertEquals(7917, parser.parseExpression(" SEVENTY-nine DOLLARS & 17/100").intValue());
		assertEquals(0, parser.parseExpression("zero & 0/100").intValue());
		assertEquals(100, parser.parseExpression("zero & 100/100").intValue());
		assertEquals(699, parser.parseExpression("six &99\\100").intValue());
		assertEquals(326, parser.parseExpression("three& 26 / 100 dollars").intValue());
		assertEquals(100, parser.parseExpression("&100/100").intValue());
		assertEquals(200, parser.parseExpression("one & 100/100").intValue());
		assertEquals(250, parser.parseExpression("2 & 50 cent").intValue());
		assertEquals(1000, parser.parseExpression("ten & 0/100").intValue());
		assertEquals(5612, parser.parseExpression("50 six & 12/100 dollars").intValue());
		assertEquals(444, parser.parseExpression("four &44/100").intValue());
		assertEquals(1037, parser.parseExpression("ten & 37/100").intValue());
		assertEquals(900, parser.parseExpression("nine & 0/100").intValue());
		assertEquals(1159, parser.parseExpression("eleven & 59/100").intValue());
		assertEquals(1861, parser.parseExpression("eighteen dollars & 61/100").intValue());
		assertEquals(1782, parser.parseExpression("seventeen&82/100").intValue());
		assertEquals(9099, parser.parseExpression("ninety & 99/100 ").intValue());
		assertEquals(9999, parser.parseExpression("ninety nine & 99/100  ").intValue());
		assertEquals(7343, parser.parseExpression(" SEVENTY-THREE DOLLAR & 43 / 100").intValue());
		assertEquals(8000, parser.parseExpression(" SEVENTY-NINE DOLLARS &100 / 100").intValue());
		assertEquals(4700, parser.parseExpression(" fourty seven dollars & zero cents ").intValue());
		assertEquals(6700, parser.parseExpression(" sixty seven dollars & without cents ").intValue());
		assertEquals(5049, parser.parseExpression("50$ & 49 cents").intValue());
	}

	@Test
	public void shouldTreatDollarsSymbol()throws Exception{
		assertEquals(755, parser.parseExpression("$7 + 55 Cent").intValue());
		assertEquals(1039, parser.parseExpression("$10 and 39/100").intValue());
		assertEquals(1116, parser.parseExpression("$eleven + 16/100").intValue());
		assertEquals(916, parser.parseExpression("$nine and 16 cents").intValue());
		assertEquals(9616, parser.parseExpression("90 six $ and 16 cents").intValue());
		assertEquals(5400, parser.parseExpression("54$").intValue());
		assertEquals(9900, parser.parseExpression(" $99 ").intValue());
		assertEquals(10000, parser.parseExpression("$100 ").intValue());
		assertEquals(100, parser.parseExpression("$100/100 ").intValue());
		assertEquals(100, parser.parseExpression("100/100 $").intValue());
		assertEquals(9101, parser.parseExpression("$91 AND 1 CENT").intValue());
		assertEquals(1704, parser.parseExpression("17$ AND FOUR CENTS").intValue());
		assertEquals(1404, parser.parseExpression("14 AND FOUR CENTS$").intValue());
		//usd
		assertEquals(1013, parser.parseExpression("USD10 and 13/100").intValue());
		assertEquals(1176, parser.parseExpression("USD$11 and 76 / 100").intValue());
	}
	
	@Test
	public void shouldTreatDotAsAnd() throws Exception{
		assertEquals(345, parser.parseExpression("3.45").intValue());
		assertEquals(789, parser.parseExpression("7.89 dollars").intValue());
		assertEquals(1, parser.parseExpression(" .01 dollar ").intValue());
		assertEquals(100, parser.parseExpression("1.00 dollar").intValue());
		assertEquals(310, parser.parseExpression("3.10 dollar").intValue());
		assertEquals(920, parser.parseExpression("9.20 dollar").intValue());
		assertEquals(4700, parser.parseExpression(" fourty-seven.0 dollar ").intValue());
	}
	
	@Test
	public void shouldReturnNullIfDotCents() throws Exception{
		assertEquals(null, parser.parseExpression("9.20 cent"));
		assertEquals(null, parser.parseExpression("1.001 dollar"));
		assertEquals(null, parser.parseExpression("1100.0103 "));
		assertEquals(null, parser.parseExpression(" fourty-seven.zero dollar "));		
		assertEquals(null, parser.parseExpression("&+ .01 dollar "));
		assertEquals(null, parser.parseExpression("9.20/100"));
		assertEquals(null, parser.parseExpression("ten.99/100"));
		assertEquals(null, parser.parseExpression("ten dollar anD .20/100"));
		
	}
	
	@Test 
	public void shouldHandleMultiDashConnect() throws Exception{
		assertEquals(7917, parser.parseExpression(" SEVENTY-nine DOLLARS --- 17/100").intValue());
		assertEquals(0, parser.parseExpression("zero --- 0/100").intValue());
		assertEquals(100, parser.parseExpression("zero --- 100/100").intValue());
		assertEquals(699, parser.parseExpression("six --- 99\\100").intValue());
		assertEquals(326, parser.parseExpression("three --- 26 / 100 dollars").intValue());
		assertEquals(100, parser.parseExpression("--- 100/100").intValue());
		assertEquals(2996, parser.parseExpression("Twenty Nine Dollar --- 96 Cents").intValue());
		assertEquals(200, parser.parseExpression("one --- 100/100").intValue());
		assertEquals(250, parser.parseExpression("two --- 50 cent").intValue());
		assertEquals(1000, parser.parseExpression("ten --- 0/100").intValue());
		assertEquals(5612, parser.parseExpression("50 six --- 12/100 dollars").intValue());
		assertEquals(444, parser.parseExpression("four ---44/100").intValue());
		assertEquals(1037, parser.parseExpression("ten --- 37/100").intValue());
		assertEquals(900, parser.parseExpression("nine --- 0/100").intValue());
		assertEquals(1159, parser.parseExpression("eleven --- 59/100").intValue());
		assertEquals(1861, parser.parseExpression("eighteen dollars --- 61/100").intValue());
		assertEquals(1782, parser.parseExpression("seventeen---82/100").intValue());
		assertEquals(9099, parser.parseExpression("ninety --- 99/100 ").intValue());
		assertEquals(9999, parser.parseExpression("ninety nine --- 99/100  ").intValue());
		assertEquals(7343, parser.parseExpression(" SEVENTY-THREE DOLLAR --- 43 / 100").intValue());
		assertEquals(8000, parser.parseExpression(" SEVENTY-NINE DOLLARS --- 100 / 100").intValue());
		assertEquals(4700, parser.parseExpression(" fourty seven dollars --- zero cents ").intValue());
		assertEquals(6700, parser.parseExpression(" sixty seven dollars --- without cents ").intValue());
	}
	
	@Test 
	public void shouldTreatetildeAsAnd() throws Exception{
		assertEquals(7917, parser.parseExpression(" SEVENTY-nine DOLLARS ~ 17/100").intValue());
		assertEquals(0, parser.parseExpression("zero ~ 0/100").intValue());
		assertEquals(100, parser.parseExpression("zero ~ 100/100").intValue());
		assertEquals(699, parser.parseExpression("six ~ 99\\100").intValue());
		assertEquals(326, parser.parseExpression("three ~ 26 / 100 dollars").intValue());
		assertEquals(100, parser.parseExpression("~ 100/100").intValue());
		assertEquals(2996, parser.parseExpression("Twenty Nine Dollar ~ 96 Cents").intValue());
		assertEquals(200, parser.parseExpression("one ~100/100").intValue());
		assertEquals(250, parser.parseExpression("two ~ 50 cent").intValue());
		assertEquals(1000, parser.parseExpression("ten ~ 0/100").intValue());
		assertEquals(5612, parser.parseExpression("50 six ~ 12/100 dollars").intValue());
		assertEquals(444, parser.parseExpression("four ~44/100").intValue());
		assertEquals(1037, parser.parseExpression("ten ~ 37/100").intValue());
		assertEquals(900, parser.parseExpression("nine ~ 0/100").intValue());
		assertEquals(1159, parser.parseExpression("eleven ~ 59/100").intValue());
		assertEquals(1861, parser.parseExpression("eighteen dollars ~ 61/100").intValue());
		assertEquals(1782, parser.parseExpression("seventeen~ 82/100").intValue());
		assertEquals(9099, parser.parseExpression("ninety ~ 99/100 ").intValue());
		assertEquals(9999, parser.parseExpression("ninety nine ~ 99/100  ").intValue());
		assertEquals(7343, parser.parseExpression(" SEVENTY-THREE DOLLAR ~ 43 / 100").intValue());
		assertEquals(8000, parser.parseExpression(" SEVENTY-NINE DOLLARS ~ 100 / 100").intValue());
		assertEquals(4700, parser.parseExpression(" fourty seven dollars ~ zero cents ").intValue());
		assertEquals(6700, parser.parseExpression(" sixty seven dollars ~ without cents ").intValue());
	}
	
	

}
