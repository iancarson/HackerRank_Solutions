import java.util.concurrent.ArrayBlockingQueue;

public class BlockingBuffer implements StringBuffer{
	private ArrayBlockingQueue<Integer> buffer;

	public BlockingBuffer()
	{
		buffer = new ArrayBlockingQueue<Integer>( 3 );
	}
	public void set(int value)
	{
		try
		{
			buffer.put(value);
			System.out.printf("%s%2d\t%s%d\n","Producer writes",value,"Buffers occupied:",buffer.size() );
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public int get()
	{
		int readValue = 0;
		try
		{
			readValue = buffer.take();//remove value from circular buffer\
			System.out.printf("%s%2d\t%s%d\n","Consumer reads",readValue,"Buffers occupied:",buffer.size());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return readValue;
	}
}