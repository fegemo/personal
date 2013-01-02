package com.guidetogalaxy.merchanteer.numberFormat;

import static org.junit.Assert.*;

import org.junit.Test;
import static com.guidetogalaxy.merchanteer.numberFormat.RomanSymbol.*;

public class RomanSymbolTest {

	@Test
	public void testCanSubtract() {
		assertFalse(RomanSymbol.canSubtract(I, I));
		assertTrue(RomanSymbol.canSubtract(V, I));
		assertTrue(RomanSymbol.canSubtract(X, I));
		assertFalse(RomanSymbol.canSubtract(L, I));
		assertFalse(RomanSymbol.canSubtract(C, I));
		assertFalse(RomanSymbol.canSubtract(D, I));
		assertFalse(RomanSymbol.canSubtract(M, I));

		assertFalse(RomanSymbol.canSubtract(I, V));
		assertFalse(RomanSymbol.canSubtract(V, V));
		assertFalse(RomanSymbol.canSubtract(X, V));
		assertFalse(RomanSymbol.canSubtract(L, V));
		assertFalse(RomanSymbol.canSubtract(C, V));
		assertFalse(RomanSymbol.canSubtract(D, V));
		assertFalse(RomanSymbol.canSubtract(M, V));

		assertFalse(RomanSymbol.canSubtract(I, X));
		assertFalse(RomanSymbol.canSubtract(V, X));
		assertFalse(RomanSymbol.canSubtract(X, X));
		assertTrue(RomanSymbol.canSubtract(L, X));
		assertTrue(RomanSymbol.canSubtract(C, X));
		assertFalse(RomanSymbol.canSubtract(D, X));
		assertFalse(RomanSymbol.canSubtract(M, X));

		assertFalse(RomanSymbol.canSubtract(I, L));
		assertFalse(RomanSymbol.canSubtract(V, L));
		assertFalse(RomanSymbol.canSubtract(X, L));
		assertFalse(RomanSymbol.canSubtract(L, L));
		assertFalse(RomanSymbol.canSubtract(C, L));
		assertFalse(RomanSymbol.canSubtract(D, L));
		assertFalse(RomanSymbol.canSubtract(M, L));

		assertFalse(RomanSymbol.canSubtract(I, C));
		assertFalse(RomanSymbol.canSubtract(V, C));
		assertFalse(RomanSymbol.canSubtract(X, C));
		assertFalse(RomanSymbol.canSubtract(L, C));
		assertFalse(RomanSymbol.canSubtract(C, C));
		assertTrue(RomanSymbol.canSubtract(D, C));
		assertTrue(RomanSymbol.canSubtract(M, C));
		
		assertFalse(RomanSymbol.canSubtract(I, D));
		assertFalse(RomanSymbol.canSubtract(V, D));
		assertFalse(RomanSymbol.canSubtract(X, D));
		assertFalse(RomanSymbol.canSubtract(L, D));
		assertFalse(RomanSymbol.canSubtract(C, D));
		assertFalse(RomanSymbol.canSubtract(D, D));
		assertFalse(RomanSymbol.canSubtract(M, D));

		assertFalse(RomanSymbol.canSubtract(I, M));
		assertFalse(RomanSymbol.canSubtract(V, M));
		assertFalse(RomanSymbol.canSubtract(X, M));
		assertFalse(RomanSymbol.canSubtract(L, M));
		assertFalse(RomanSymbol.canSubtract(C, M));
		assertFalse(RomanSymbol.canSubtract(D, M));
		assertFalse(RomanSymbol.canSubtract(M, M));
	}

	@Test
	public void testFromCharacter() throws MalformedNumberException {
		assertEquals(I, RomanSymbol.fromCharacter('I'));
		assertEquals(V, RomanSymbol.fromCharacter('V'));
		assertEquals(X, RomanSymbol.fromCharacter('X'));
		assertEquals(L, RomanSymbol.fromCharacter('L'));
		assertEquals(C, RomanSymbol.fromCharacter('C'));
		assertEquals(D, RomanSymbol.fromCharacter('D'));
		assertEquals(M, RomanSymbol.fromCharacter('M'));
	}

}
