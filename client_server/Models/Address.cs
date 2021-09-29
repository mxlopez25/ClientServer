using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace client_server.Models
{
    public class Address
    {
        public int AddressId { get; set; }
        public int UserId { get; set; }
        public string Street1 { get; set; }
        public string Street2 { get; set; }
        public string City { get; set; }
        public string ZipCode { get; set; }
        public string Phone { get; set; }

        [ForeignKey("UserId")]
        public User User { get; set; }
    }
}
