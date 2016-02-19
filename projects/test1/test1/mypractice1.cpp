#include<iostream>
using namespace std;
int main()
{
	char c;
	cout<<"please give me your sentence:";
	c=getchar();
	while(c!='\n'){
		if(c<'Z' && c>'A'){cout<<c;c=getchar();}
		else if(c==' '){
			cout<<' ';
			while(c=getchar()==' ');
		}
		else c=getchar();
	}
	return 0;
}