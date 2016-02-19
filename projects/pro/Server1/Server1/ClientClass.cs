using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Server1
{
    class ClientClass
    {
        public ClientClass(String name,String message,int port)
        {
            this.Name = name;
            this.Message = message;
            this.port = port;
            this.alive = true;
            this.count = 5;
        }
        private String message;
        private String name ;
        private int port ;
        private bool alive ;
        private int count;

        private String Message { get { return message; } set{message = ; }
        private String Name { get { return name; } set; }
        private int Port { get { return port; } set; }
        private bool Alive { get { return alive; } set; }
        private int Count;
    }
}
