namespace CookieBakery
{
	public class Customer
	{
		public string Name { get; private set; }

		public Customer(string name)
		{
			Name = name;
		}

		public override string ToString()
		{
			return Name;
		}
	}
}