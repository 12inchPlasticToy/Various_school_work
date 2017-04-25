using System;
using System.Collections.Generic;
using System.Threading;

namespace CookieBakery
{
/**
			    * 
			    * Has Customers Fred, Ted, and Greg
			    * 
			    * Implement flavors
			    * 
			    * If time, Make one spesific Glutenfree Cookie For "Need glutenfreeCookie Greg"
			    * 
			    * USE:
			    * Factory DP
			    * 
			    * Fred, Ted and Greg makes calls to this thread through their own threads.
			    * Example every 100 millisec. (Number sligthy higher than backin time)
			    * This Method needs this name because it says so in out asignment
			    * 
			    * 
			    * */

	internal class Program
	{
		/*
		static Queue<int> cookies = new Queue<int>();
		const int CookieThreads = 3;
		static int[] sums = new int[CookieThreads];
		*/
		private static Bakery _bakery;
		private static List<Customer> _customers;
		private static Thread _factoryThread;
		private static Thread _customerThread;

		public static void Main()
		{
			_bakery = new Bakery("Mamma's");

			_customers = new List<Customer> {new Customer("Ted"), new Customer("Fred"), new Customer("Greg")};

			_factoryThread = new Thread(_bakery.ProduceCookies);
			_customerThread = new Thread(CustomerBuys);

			_factoryThread.Start();
			_customerThread.Start();

			_factoryThread.Join();
			_customerThread.Join();
		}

		private static void CustomerBuys()
		{
			Random rand = new Random();
			while (_factoryThread.IsAlive || _bakery.CookieQueue.Count > 0)
			{
				int index = rand.Next(_customers.Count);
				_bakery.SellCookiesTo(_customers[index]);
			}
		}
	}
}
