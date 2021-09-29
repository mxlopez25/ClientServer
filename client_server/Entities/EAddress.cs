using System;
namespace client_server.Entities
{
    public class EAddress
    {
        public int AddressId { get; set; }
        public string Street1 { get; set; }
        public string Street2 { get; set; }
        public string City { get; set; }
        public string ZipCode { get; set; }
        public string Phone { get; set; }
        public EUser User { get; set; }
    }
}
