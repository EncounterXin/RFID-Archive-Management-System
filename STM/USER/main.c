#include "delay.h"
#include "key.h"
#include "sys.h"
#include "usart.h"
#include "uart2.h"
#include "uart3.h"
#include "ds18b20.h"
#include "wifi.h"
#include "gpio.h"
#include "stdio.h"
#include "glr_adc.h"
#include "rc522.h"
extern __IO u16 data_value[6];
u32		AD1_value, hy, trq, yw, AD5_value;
//�¶ȱ���ֵ
 double	temperatureLimit;
 int	flameLimit;
 int	gasLimit;
 int	smokeLimit;
u8		ds18b20_state;
int		i;





/**
 *   ����˵����
 *   1--SDA  <----->PA4
 *   2--SCK  <----->PA5
 *   3--MOSI <----->PA7
 *   4--MISO <----->PA6
 *   5--����
 *   6--GND <----->GND
 *   7--RST <----->PB0
 *   8--VCC <----->VCC
 **/


/* a9 a10 ��Ƭ���������  a2 a3 wifiʹ�� PB1 - ������  PB8-������ PB9-���� PC15-���� */

void init( void )
{
	DELAY_INIT(); //��ʼ��ʱ�� �ӳٺ���ʹ��
	NVIC_PriorityGroupConfig( NVIC_PriorityGroup_2 ); /* ����NVIC�жϷ��� */
	USART1_INIT( 115200 );//����1��ʼ��
	USART2_INIT( 115200 );//����2��ʼ��




//	ADC_INIT();
	LED1_INIT(); /* ��ʼ���ƺͷ��� */
//	LED2_INIT();
//	FAN_INIT();
	//BUZZER_INIT();
//	/* �������� */
//	GPIO_SetBits( GPIOB, GPIO_Pin_8 );

	delay_ms(10000 );
	ESP_INIT();
//	ds18b20_state = DS18B20_Init(); //��ʼ���¶ȴ�����
//	GPIO_SetBits( GPIOB, GPIO_Pin_9 );
	printf("wifi��ʼ���ɹ�");
		RC522_Init();    
}


int main( void )
{
	float wd = 0;
	int num = 0;

	u8	p[100]		= "";
//	char	cRes[512]	= "";
   init();
	printf("ϵͳ��ʼ���ɹ�\n");
			GPIO_SetBits(GPIOB,GPIO_Pin_12);	
		delay_ms( 500 );
		GPIO_ResetBits(GPIOB,GPIO_Pin_12);	
		while ( 1 )
		{
			
			RC522_Handel();
			delay_ms( 10 );
			num ++;
			if(num>100){
				num = 0;
				printf("发送心跳包 \r\n");
				//发送心跳包
				// u2_printf( "GET http://192.168.28.237:9999/user/test\r\n", p );
				u2_printf( "GET http://server.rams.encounterdx.live/user/test\r\n", p );
			} 
		}
}


