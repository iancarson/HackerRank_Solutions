public class UnsynchronizedBuffer implements Buffer 
{
	private int Buffer = -1;
	public void set(int value)
	{
		System.out.printf("Producer writes \t%2d",value);
		buffer = value;
	}
	public int get()
	{
		System.out.printf("Consumer reads\t%2d",buffer);
		return buffer;
	}
}