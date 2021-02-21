import random
max_PrimLength = 1000000000000

def extended_gcd(a, b):
    if a == 0:
        return (b, 0, 1)
    else:
        g, y, x = extended_gcd(b % a, a)
        return (g, x - (b // a) * y, y)

def gcd(a, b):
    while b != 0:
        a, b = b, a % b
    return a


#checks if a number is a prime

def is_prime(num):
    if num == 2:
        return True
    if num < 2 or num % 2 == 0:
        return False
    for n in range(3, int(num**0.5)+2, 2):
        if num % n == 0:
            return False
    return True

def generateRandomPrime():
    while(1):
        ranPrime = random.randint(0,max_PrimLength)
        if is_prime(ranPrime):
            return ranPrime

def generate_keyPairs():
    p = generateRandomPrime()
    q = generateRandomPrime()
    #p = 2074722246773485207821695222107608587480996474721117292752992589912196684750549658310084416732550077;q = 2367495770217142995264827948666809233066409497699870112003149352380375124855230068487109373226251983
    print("\nDerived Prime Numbers p,q are ",p,q)
    n = p*q
    print("\nRSA modulus N : ",n)
    phi = (p-1) * (q-1) 
    print("\nNext Step is to find Totient Φ(N) = (p-1) * (q-1)\n\nΦ(N) = ",phi)   
    e = random.randint(1, phi)
    g = gcd(e,phi)
    while g != 1:
        e = random.randint(1, phi)
        g = gcd(e, phi)
        
    print("\nDerived Number e = ",e)
    d = extended_gcd(e, phi)[1]
    d = d % phi
    if(d < 0):
        d += phi
        
    return ((e,n),(d,n))
        
def decrypt(ctext,private_key):
    try:
        key,n = private_key
        print("\nDecryption is done as P = "+str(ctext)+"^"+str(key)+" mod ("+str(n)+")")
        x = pow(ctext,key,n)
        return x
    except TypeError as e:
        print(e)

def encrypt(text,public_key):
    key,n = public_key
    print("\nEncryption is done as C = "+str(text)+"^"+str(key)+" mod ("+str(n)+")")
    x = pow(text,key,n)
    return x

if __name__ == '__main__':
    public_key,private_key = generate_keyPairs() 
    print("\nPublic Key (N,e) is ( "+str(public_key[0])+" , "+str(public_key[1])+" )")
    print("\nPrivate key d is found by computing inverse of e mod ( Φ(N) ) :\n\nd =",private_key[0])
    msg= int(input("\nEnter the number you wish to encrypt: "))
    ctext = encrypt(msg,public_key)
    print("\nEncrypted Message : ",ctext)
    plaintext = decrypt(ctext, private_key)
    print("\nDecrypted Message :",plaintext)
    print("\n")