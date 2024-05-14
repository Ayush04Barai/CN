/*Write a program to demonstrate sub-nets  and find the subnet masks */
#include <iostream>
#include <string>
#include <sstream>
#include <bitset>

using namespace std; // Use the std namespace for convenience

// Function to calculate subnet details
void calculateSubnet(const string& ipAddress, int prefixLength) {
    // Parse the IP address
    unsigned long ip = 0;
    istringstream iss(ipAddress);
    string token;
    int i = 24;
    while (getline(iss, token, '.')) {
        ip |= (stoul(token) << i);
        i -= 8;
    }

    // Calculate subnet mask
    unsigned long subnetMask = (~0UL) << (32 - prefixLength);

    // Calculate network address and broadcast address
    unsigned long networkAddress = ip & subnetMask;
    unsigned long broadcastAddress = networkAddress | (~subnetMask);

    // Display subnet details
    cout << "Subnet Details:" << endl;
    cout << "IP Address: " << ipAddress << endl;
    cout << "Subnet Mask: " << ((subnetMask >> 24) & 0xFF) << "." << ((subnetMask >> 16) & 0xFF) << "." 
         << ((subnetMask >> 8) & 0xFF) << "." << (subnetMask & 0xFF) << endl;
    cout << "Network Address: " << ((networkAddress >> 24) & 0xFF) << "." << ((networkAddress >> 16) & 0xFF) << "." 
         << ((networkAddress >> 8) & 0xFF) << "." << (networkAddress & 0xFF) << endl;
    cout << "Broadcast Address: " << ((broadcastAddress >> 24) & 0xFF) << "." << ((broadcastAddress >> 16) & 0xFF) << "." 
         << ((broadcastAddress >> 8) & 0xFF) << "." << (broadcastAddress & 0xFF) << endl;

    // Calculate usable IP address range
    unsigned long firstUsable = networkAddress + 1;
    unsigned long lastUsable = broadcastAddress - 1;

    cout << "Usable IP Range: " << ((firstUsable >> 24) & 0xFF) << "." << ((firstUsable >> 16) & 0xFF) << "." 
         << ((firstUsable >> 8) & 0xFF) << "." << (firstUsable & 0xFF) << " - "
         << ((lastUsable >> 24) & 0xFF) << "." << ((lastUsable >> 16) & 0xFF) << "." 
         << ((lastUsable >> 8) & 0xFF) << "." << (lastUsable & 0xFF) << endl;
}

int main() {
    string ipAddress;
    int prefixLength;

    // Input IP address and prefix length
    cout << "Enter IP address (e.g., 192.168.1.1): ";
    cin >> ipAddress;
    cout << "Enter subnet mask prefix length (e.g., 24 for /24): ";
    cin >> prefixLength;

    // Calculate and display subnet details
    calculateSubnet(ipAddress, prefixLength);

    return 0;
}
