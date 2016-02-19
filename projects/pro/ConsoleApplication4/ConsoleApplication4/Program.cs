using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net.Sockets;
using System.Net;

namespace ConsoleApplication4
{
    class Program
    {
        static void Main(string[] args)
        {

            Socket sck = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp); 
            sck.Connect(IPAddress.Parse("127.0.0.1"), 12345);
            while (true) 
            {
               // String str =  Console.ReadLine();
                sck.Send(Encoding.ASCII.GetBytes(Console.ReadLine()));
            
                //sck.Send(Encoding.ASCII.GetBytes("hello,this is Client!")); 
            }
            sck.Close();

            Console.ReadKey();

        }
    }
}
