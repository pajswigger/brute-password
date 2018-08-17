# Brute Password

This Burp extension lets you use Intruder to brute force the password in platform authentication.
To use, first set up platform authentication with the right user name and authentication type.
You need to include an unused header (e.g. "Foo") in the header, where the header value is an
Intruder position. Configure your word list for the payload, then add a payload processor that invokes a Burp extension, with
the hander *Set platform auth password*. In options you need to limit the number of threads to 1. Each time the payload
processor is invoked it will update the first set of credentials in the project options.