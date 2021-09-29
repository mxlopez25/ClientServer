using System.Collections.Generic;
using System.Threading.Tasks;
using client_server.Models;

namespace client_server.Services.Interfaces {
    public interface IUserService
    {
        Task<List<User>> All();
        Task<User> Get(int Id);
        Task<User> Create(User user);
        Task<User> Update(int Id, User user);
        Task<bool> Delete(int Id);
    }
}