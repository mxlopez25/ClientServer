using System;
using System.Collections.Generic;

namespace client_server.Models {
    public class User {
        public int UserId { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public DateTime CreatedDate { get; set; }

        public List<Address> Addresses { get; set; }
    }
}