using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using client_server.Models;

namespace client_server.Services.Interfaces
{
    public interface IAddressService
    {
        Task<List<Address>> All();
        Task<Address> Get(int Id);
        Task<List<Address>> GetByUserId(int Id);
        Task<Address> Create(Address address);
        Task<Address> Update(int Id, Address address);
        Task<bool> Delete(int Id);
    }
}
