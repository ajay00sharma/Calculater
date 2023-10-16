#include <iostream>
#include <stack>
#include <math.h>

using namespace std;
class calculate
{
    stack<char> in;
    stack<int> post;

public:
    int priority(char formula)
    {
        if (formula == '^')
            return 3;
        else if (formula == '*' || formula == '/')
            return 2;
        else if (formula == '+' || formula == '-')
            return 1;
        else
            return 0;
    }
    double evaluate(string s)
    {
        for (int i = 0; i < s.length(); i++)
        {
            if (s[i] >= '0' && s[i] <= '9')
            {
                post.push(s[i] - '0'); // subtracting from 0 char gives int
            }
            else
            {
                double operand2 = post.top();
                post.pop();
                double operand1 = post.top();
                post.pop();

                switch (s[i])
                {
                case '+':
                    post.push(operand1 + operand2);
                    break;
                case '-':
                    post.push(operand1 - operand2);
                    break;
                case '*':
                    post.push(operand1 * operand2);
                    break;
                case '/':
                    post.push(operand1 / operand2);
                    break;
                case '^':
                    post.push(pow(operand1, operand2));
                    break;

                default:
                    cout <<"Invalid symbole is being entered" << endl;
                    cout << "Enter the correct expression" << endl;
                    break;
                }
            }
        }
        return post.top();
    }

    void intopost(string s)
    {
        string converted;
        for (int i = 0; i < s.length(); i++)
        {
            if (s[i] == '(')
            {
                in.push(s[i]);
            }
            else if (s[i] == ')')
            {
                while (in.top() != '(' && !in.empty())
                {
                    converted += in.top();
                    in.pop();
                }
                in.pop();
            }
            else if (s[i] == '*' || s[i] == '/' || s[i] == '+' || s[i] == '-')
            {
                while (!in.empty() && priority(s[i]) <= priority(in.top()))
                {
                    converted += in.top();
                    in.pop();
                }
                in.push(s[i]);
            }
            else
                converted += s[i];
        }
        while (!in.empty()) {
            converted += in.top();
            in.pop();
        }
        cout << endl;
        cout << "The value of the above postfix expression is:-" << evaluate(converted) << endl;
    }
};

int main()
{
    calculate obj;
    cout << "Enter a expression to calculate it's value" << endl;
    string expression;
    getline(cin, expression);
    obj.intopost(expression);
    return 0;
}
