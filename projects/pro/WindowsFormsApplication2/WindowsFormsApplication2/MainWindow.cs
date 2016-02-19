using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Threading;
using System.Net;
using System.Net.Sockets;

namespace WindowsFormsApplication2
{
    public partial class MainWindow : Form
    {
        public MainWindow()
        {
            InitializeComponent();
            
            height = 500;
            this.Location = new Point(10, 10);
            LayoutMdi(MdiLayout.TileHorizontal);
            Thread SocketTread = new Thread(new ThreadStart(SocketRun));
            SocketTread.Start(); 

        }

        private void listView1_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        private void listBox1_SelectedIndexChanged(object sender, EventArgs e)
        {

        }

        private void button1_Click(object sender, EventArgs e)
        {
 


            this.textBox1.Clear();       
            
        }

        private void SocketRun()
        {
            IPAddress ia = IPAddress.Parse("127.0.0.1");
            IPEndPoint ie = new IPEndPoint(ia, 12345);

            Socket sck = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);

            sck.Bind(ie);
            sck.Listen(10);

            Socket client = sck.Accept();//如果客户端没有连接来，则会阻塞到这里 
            byte[] data = new byte[1024];
            while (KeepContect)
            {
                int len = client.Receive(data);
                if (len > 1)
                    this.MessageList.Add(CMessage(new Label(),);
                this.Display();
            }  
            
            client.Close();

            sck.Close();

       }
         

       

        private void Display()
        {
        }

        internal class CMessage
        {
            public void CMessage(Label label, String name, String message)
            {
                this.label = label;
                this.Name = name;
                this.Message = message;
                if(type = 0)
            }
            public Label label;
            String Name;
            String Message;
            int type;
        }

        private List<CMessage> MessageList = new List<CMessage>();
        private static int height;
        bool KeepContect = true;
    }
}
