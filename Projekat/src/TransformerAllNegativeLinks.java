
public class TransformerAllNegativeLinks<N> implements EdgeTransformer<N> {
	@Override
	public int transform(N x, N y) {
		return -1;
	}
}
