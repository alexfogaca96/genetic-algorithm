package br.com.ia.genetic.algorithm.model.converter;

import java.util.List;

import br.com.ia.genetic.algorithm.model.Binary;

/**
 * <p>
 * Dado um n�mero de dimens�es (D) e um {@link Binary}, converte para D
 * {@link Binary} dividindo sequencialmente.
 * </p>
 * Ex. <b>01101001 e 2 dimens�es -> 0110 e 1001</b>
 * <p>
 * Assume-se que o n�mero de bits do {@link Binary} dado � divis�vel por D.
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
