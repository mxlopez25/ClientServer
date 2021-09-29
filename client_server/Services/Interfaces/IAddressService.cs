using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using client_server.Entities;
using client_server.Models;

namespace client_server.Services.Interfaces
{
    public interface IAddressService
    {
        Task<List<EAddress>> All();
        Task<EAddress> Get(int Id);
        Task<List<EAddress>> GetByUserId(int Id);
        Task<EAddress> Create(Address address);
        Task<EAddress> Update(int Id, Address address);
        Task<bool> Delete(int Id);
    }
}
