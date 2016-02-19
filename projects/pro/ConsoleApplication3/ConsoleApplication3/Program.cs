using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;
using System.Net.Sockets;

namespace ConsoleApplication3
{
    class Program
    {
        static void Main(string[] args)
        {

           
            IPAddress ia = IPAddress.Parse("127.0.0.1"); 
            IPEndPoint ie = new IPEndPoint(ia, 12345); 

            Socket sck = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp); 
 
            sck.Bind(ie); 
            sck.Listen(10); 
 
            Socket client = sck.Accept();//如果客户端没有连接来，则会阻塞到这里 
            byte[] data = new byte[1024];
            int len = client.Receive(data); 
            Console.WriteLine(Encoding.ASCII.GetString(data, 0, len)); 
            client.Close(); 
 
            sck.Close();
            Console.ReadKey();



        }
    }
}
