using System;
using System.Collections.Generic;

namespace client_server.Entities
{
    public class EUser
    {
        public int UserId { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public List<EAddress> Addresses { get; set; }
    }
}
