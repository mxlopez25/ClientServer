using System.Collections.Generic;
using System.Threading.Tasks;
using client_server.Models;
using client_server.Services.Interfaces;

namespace client_server.Services
{
    public class UserService : IUserService
    {
        public UserService()
        {

        }

        public async Task<List<User>> All()
        {
            throw new System.NotImplementedException();
        }

        public async Task<User> Create(User user)
        {
            throw new System.NotImplementedException();
        }

        public async Task<bool> Delete(int Id)
        {
            throw new System.NotImplementedException();
        }

        public async Task<User> Get(int Id)
        {
            throw new System.NotImplementedException();
        }

        public async Task<User> Update(int Id, User user)
        {
            throw new System.NotImplementedException();
        }
    }
}