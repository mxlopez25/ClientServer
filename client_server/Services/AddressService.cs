using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using client_server.Models;
using client_server.Services.Interfaces;

namespace client_server.Services
{
    public class AddressService : IAddressService
    {
        public AddressService()
        {
        }

        public async Task<List<Address>> All()
        {
            throw new NotImplementedException();
        }

        public async Task<Address> Create(Address address)
        {
            throw new NotImplementedException();
        }

        public async Task<bool> Delete(int Id)
        {
            throw new NotImplementedException();
        }

        public async Task<Address> Get(int Id)
        {
            throw new NotImplementedException();
        }

        public async Task<List<Address>> GetByUserId(int Id)
        {
            throw new NotImplementedException();
        }

        public async Task<Address> Update(int Id, Address address)
        {
            throw new NotImplementedException();
        }
    }
}
