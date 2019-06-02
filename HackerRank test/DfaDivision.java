import java.util.Arrays;

class DfaDivision
{
	void makeTransTable(int n, int transTable[][])
	{
		int zeroTrans, oneTrans;
		for(int state = 0; state < n; ++state)
		{
			zeroTrans = state<<1;//next state to zero
			transTable[state][0] = (zeroTrans < n) ? zeroTrans : zeroTrans - n;
			oneTrans = (state<<1) + 1;//next state for bit 1
			transTable[state][1] = (oneTrans < n) ? oneTrans : oneTrans -n;
		}
	}
	void chechkState(int num, int state, int Table[][])
	{
		if(num != 0)//shift  number from right to left until 0
		{
			chechkState(num>>1, state, Table);
			state = Table[state][num&1];
		}
	}
	int isDivisible(int num, int k)
	{
		int table[][2] =  new int[k][2];//create Transition Table
		makeTransTable(k, table);
		int state = 0;//initially control in 0 state
		chechkState(num, state, table);
		return state;
	}
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		int num = in.nextInt();
		int k = in.nextInt();
		int rem = isDivisible(num, k);
		if (rem == 0) {
			System.out.println("%d is divisible by %d", num,k);
			
		}
		else
			System.out.println("%d is not divisible by %d and the remainder is %d",num,k, rem);
	}
}