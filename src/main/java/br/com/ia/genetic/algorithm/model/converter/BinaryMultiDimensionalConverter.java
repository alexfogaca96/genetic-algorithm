package br.com.ia.genetic.algorithm.model.converter;

import java.util.List;

import br.com.ia.genetic.algorithm.model.Binary;

/**
 * <p>
 * Dado um número de dimensões (D) e um {@link Binary}, converte para D
 * {@link Binary} dividindo sequencialmente.
 * </p>
 * Ex. <b>01101001 e 2 dimensões -> 0110 e 1001</b>
 * <p>
 * Assume-se que o número de bits do {@link Binary} dado é divisível por D.
 * </p>
 */
public final class BinaryMultiDimensionalConverter
{
	public static List<Binary> convert(
		final Binary binary,
		final int numberOfDimensions )
	{
		throw new UnsupportedOperationException( "Still not implemented." );
	}
}
