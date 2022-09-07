
public class TransformerOneNegativeLink<N> implements EdgeTransformer<N> {
	@Override
	public int transform(N x, N y) {
		if ((Integer.parseInt(x.toString()) == 1 && Integer.parseInt(y.toString()) == 3) || (Integer.parseInt(x.toString()) == 3 && Integer.parseInt(y.toString()) == 1)) {
			return -1;
		}
		else {
			return 1;
		}
		
	}
}
