using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Net;
using System.Net.Sockets;

namespace Server1
{
    class Program
    {
        static void Main(string[] args)
        {
            IPAddress ia = IPAddress.Parse("127.0.0.1");
            IPEndPoint ie = new IPEndPoint(ia, 12345);
            String[] mess = new String[3];
            
            Socket sck = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

            sck.Bind(ie);
            sck.Listen(10);

            while (KeepContect)
            {
                System.Threading.Thread.Sleep(500); 
                Socket client = sck.Accept();//如果客户端没有连接来，则会阻塞到这里 
                byte[] data = new byte[1024];

                int len = client.Receive(data);
                if (len > 1)
                {
                    
                    String a = Encoding.ASCII.GetString(data, 0, len);
                    mess = a.Split(new char[]{'#'});
                    //ClientClass clientmess = new ClientClass(mess[0], mess[1], int.Parse(mess[2]));
                }
                ClientClass clientmess = new ClientClass(mess[0],mess[1],int.Parse(mess[2]));
                switch(clientmess.
            

                    
               client.Close();
            
                
            }  
            
            

            sck.Close();



        }
        private static bool KeepContect = true;
        private static List<ClientClass> ClientList = new List<ClientClass>();
    }
}
