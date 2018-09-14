public static void main(String [] args)
  {
    Scanner in=new Scanner(System.in);
    int n=in.nextInt();
    int people = 5;
    int j=0;
    for(int i=0;i<n;i++)
    {
      people =(int) Math.floor(people/2);
      j + = people;
      people *= 3;
    }
    System.out.println(j);
  }