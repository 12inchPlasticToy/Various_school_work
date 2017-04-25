namespace CookieBakery
{
	public class Cookie
	{
		public string type { get; private set; }
		public int bakingOrder { get; set; }

		public Cookie(string cookieType)
		{
			type = cookieType;
		}
	}
}