using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using client_server.Models;
using client_server.Services.Interfaces;
using Microsoft.EntityFrameworkCore;

namespace client_server.Services
{
    public class AddressService : IAddressService
    {
        private readonly DataContext _dataContext;
        private readonly IUserService _userService;

        public AddressService(DataContext dataContext, IUserService userService)
        {
            _dataContext = dataContext;
            _userService = userService;
        }

        public async Task<List<Address>> All()
        {
            var addresses = await _dataContext.Addresses.ToListAsync();
            return addresses;
        }

        public async Task<Address> Create(Address address)
        {
            var user = await _userService.Get(address.UserId);
            if(user == null)
            {
                return null;
            }
            _dataContext.Addresses.Add(address);
            await _dataContext.SaveChangesAsync();
            return address;
        }

        public async Task<bool> Delete(int Id)
        {
            throw new NotImplementedException();
        }

        public async Task<Address> Get(int Id)
        {
            var address = await _dataContext.Addresses.FindAsync(Id);
            return address;
        }

        public async Task<List<Address>> GetByUserId(int Id)
        {
            var addresses = await _dataContext.Addresses.Where(a => a.UserId == Id).ToListAsync();
            return addresses;
        }

        public async Task<Address> Update(int Id, Address address)
        {
            throw new NotImplementedException();
        }
    }
}
