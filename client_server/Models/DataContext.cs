using System;
using Microsoft.EntityFrameworkCore;

namespace client_server.Models
{
    public class DataContext : DbContext
    {
                public DbSet<User> Users { get; set; }
        public DbSet<Address> Addresses { get; set; }
        public string DbPath { get; private set; }

        public DataContext() {
            var folder = Environment.SpecialFolder.LocalApplicationData;
            var path = Environment.GetFolderPath(folder);
            DbPath = $"{path}{System.IO.Path.DirectorySeparatorChar}blogging.db";
        }

        // The following configures EF to create a Sqlite database file in the
        // special "local" folder for your platform.
        protected override void OnConfiguring(DbContextOptionsBuilder options)
            => options.UseSqlite($"Data Source={DbPath}");

    }
}
