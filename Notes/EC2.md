## AWS EC2

### EC2 Introduction
Amazon's Elastic Compute Cloud (EC2) is a web service that provides secure, re-sizable compute capacity in the cloud. It is designed to make web-scale cloud computing easier for developers. EC2s offer the following:
- Virtual Computing Environments (images)
    - pre-configured templates for your images known as Amazon Machine Images (AMIs). These include the OS and additional software you need
- Various instance types. These have differing CPUs, memory, storage, and networking capacity
    - an instance is a virtual server in the cloud
- secure login information for your instance
    - Amazon holds a public key, you keep a private key
    - security groups that determine who can access your instance via protocols, ports, and IP ranges
- Complete Control
    - As the creator of the instance you have root access to each of your instances, and you can start and stop them without losing access to the data they hold.
- Flexible Cloud Hosting Services
    - You can mix and match operating systems, cpu, memory, etc with your instances, creating exactly what you need to manage and run your web product.
- Elasticity
    - The "Elasticity" of EC2s is their ability to auto scale both up and down: when your system has higher demand the instance can add more processing power to handle the load, and when the traffic dies down it can revert to a lower processing power, saving you money in the long run.
    
### Security Groups
A Security Group controls incoming and outgoing traffic to and from one or more resources (like an EC2 instance). A VPC, or Virtual Private Cloud, is a logical grouping of AWS resources into a virtual network. A Security Group can only be applied to resources which exist inside its VPC.

Security Groups control traffic with rules. Rules make determinations about what traffic is allowed based on the following parameters:
- Protocol
- Port
- ICMP Type (Internet Control Message Protocol)
- Source or Destination

### SSH into EC2
EC2 instances can be opened up to be public facing and will be assigned a public address on the internet which can be used to access it remotely. SSH, or Secure Shell, is a network protocol that gives users a secure way to access a computer over an unsecured network. SSH is built atop the TCP protocol, and port 22 is commonly reserved for SSH traffic.

Access via the web console:
- On your AWS Management Console, navigate to EC2 > Instances
- Click on the instance you wish to access
- Click Connect at the top of the Instance Summary
- Click the orange Connect button
- This should open the web console and initiate an SSH connection to the server.

Access via local terminal: **NOTE** You will need a public/private key pair to access the server. This key should have been generated during the EC2 initialization process. If not, you can generate one and assign it to your instance
- On your AWS Management Console, navigate to EC2 > Instances
- Click on the instance you wish to access
- Click Connect at the top of the Instance Summary
- Select the SSH Client tab
- Instructions are given here, with an example console command. You will need to make sure you adjust the command to accurately reference the key file. You may need to adjust the file security settings on the key file, depending on the OS you are working with locally

### AMI
An image in this context refers to a binary snapshot of the state of a machine at a given moment. This image can be used to re-create that exact state. Generally, this is used to load software onto a system without having to install each item individually. When it comes to virtual machines, installing an OS and a bunch of software would be far too time consuming. So, instead, VMs launch with an image of a working machine and are ready almost immediately. Amazon Machine Images (AMIs) are images maintained and supported by AWS to be used to launch EC2 instances. They come in the form of ready-to-go operating systems with pre-installed software and pre-configured settings. There are AMIs for nearly any OS you can imagine, including many versions of Windows, MacOS, and Linux distributions. Amazon Linux 2 is a very common AMI, based on Amazon's own Linux distribution. It is optimized to work in the EC2 environment, and comes loaded with a minimal set of software packages to integrate with AWS services and act as a high-performance execution environment

### EBS (Elastic Block Storage)
Amazon EBS offers block storage volumes for EC2 instances. Block storage is the technology that nearly all file systems are built on top of. These can be attached to an EC2 instance where they act like any storage volume, but persist beyond the life of the instance. EBS volumes can be dynamically resized and reconfigured even while attached to an instance. EBS volumes are frequently used as the primary storage volume in EC2 instances where they can emulate both solid-state and hard drives. You can back up the data on your Amazon EBS volumes to Amazon S3 by taking point-in-time snapshots. Snapshots are incremental backups, which means that only the blocks on the device that have changed after your most recent snapshot are saved.

Another feature of EBS is volume encryption: this allows you to encrypt any data not currently being utilized by an EC2 instance, which makes for secure data storage and travel, should you need to change the location of a snapshot