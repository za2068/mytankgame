//��Ϸ����  
#ifndef __snakegame__GameScene__  
#define __snakegame__GameScene__  
#include "cocos2d.h"  
USING_NS_CC;  
//����һ����Ϸ��ö����������ʶ̰���ߵ��ƶ�����  
enum  class ENUM_DIR  
{  
    DIR_UP,  
    DIR_DOWN,  
    DIR_LEFT,  
    DIR_RIGHT,  
    DIR_STOP  
};  
//�߾���  
class SnakeNode:public Sprite//��װ�ڵ�  
{  
public:  
      
    ENUM_DIR m_dir;//�ƶ�����  
    int nodeType;//��ǰ���� 1����ͷ2������3ʳ��  
    int m_row, m_col; //��ǰ�ڵ����������  
  
    SnakeNode();  
    ~SnakeNode();  
    static SnakeNode* create(int type);  
    virtual bool init(int type);  
    void setPositionRC(int row, int col);//���ýڵ������  
};  
//��Ϸ����ͼ��  
class Game:public Layer{  
public:  
    SnakeNode* spFood;//ʳ��  
    SnakeNode* spHead;//��ͷ  
    int m_score; //��  
    Game();  
    ~Game();  
    Vector<SnakeNode*>allBody;//���壬���峤���ǲ�ȷ���ģ���ȷ����������������Vector  
    static Scene* createScene();  
    CREATE_FUNC(Game);  
    virtual bool init();  
    void menuCallBack(Ref* object);  
    void  gameLogic(float);  
    void newBody();//���һ���µ�����ڵ�  
    void moveBody();//�ƶ����е�����ڵ�  
};  
#endif  