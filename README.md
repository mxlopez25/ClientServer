# Client-Server Android and .NET Core 5 Example

This Client-Server example is to show a basic way to understand how to integrate a .NET Core Wep api and 
an Android app. The app and the service is make to handle the creation of users and add addresses to each
of them.

### Important:
    This app is connected to an azure database and web app service for a limited time only, for testing purposes, this will be removed shortly after.


## .NET Setting up
To start setting up the .NET Core service, first have to install the .NET Core 5 SDK.
Go to this link to donwload it:

https://dotnet.microsoft.com/download/dotnet/5.0

After the .NET Core 5 SDK is installed, need to install the EF Core CLI, run the next command in
the terminal:

```dotnet tool install --global dotnet-ef```

After installation, make sure the appsettings.json connection string is correct. Then run
the command:

```dotnet restore```

After all the packages are restored, run the following command to run the migrations in the desired database:

```dotnet ef database update```

Lastly, run the command:

```dotnet run```

To start the service.


## Android Setting Up

First, needs to make sure that URL variable in the ``` Constants.java ```, then import the project into Android Studio and run it on a device with Android 7.0 (Nougat) or higher. For the app to run correctly, the service must be running and accessible.