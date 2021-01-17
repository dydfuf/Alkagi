import pygame
from Button import Button
# from Form import Form

class MainMenu:
    BG = (255,255,255)

    def __init__(self):
        self.WIDTH = 1000
        self.HEIGHT = 1000
        self.win = pygame.display.set_mode((self.WIDTH, self.HEIGHT))
        self.name = ""
        self.password = ""
        self.name_font = pygame.font.SysFont("comicsans", 80)
        self.password_font = pygame.font.SysFont("comicsans", 80)
        self.title_font =  pygame.font.SysFont("comicsans", 120)
        self.enter_font =  pygame.font.SysFont("comicsans", 60)
        self.loginButton = Button("Login", self.WIDTH/2, self.HEIGHT*0.8)
        self.typing = True

    def draw(self):
        self.win.fill(self.BG)
        title = self.title_font.render("Alkagi", 1, (0,0,0))
        self.win.blit(title, (self.WIDTH/2 - title.get_width()/2, 50 ))

        name = self.name_font.render("Type a Name : " + self.name, 1, (0,0,0))
        self.win.blit(name, (100, 400))

        password = self.name_font.render("Type a Password : " + self.password, 1, (0,0,0))
        self.win.blit(password, (100, 600))

        self.loginButton.draw(self.win)

        pygame.display.update()

    def run(self):
        run = True
        clock = pygame.time.Clock()

        while run:
            clock.tick(60)

            self.draw()

            for event in pygame.event.get():
                if event.type == pygame.QUIT:
                    run = False
                    pygame.quit()
                    quit()

                if event.type == pygame.KEYDOWN:
                    key_name = pygame.key.name(event.key)
                    # converts to uppercase the key name
                    # key_name = key_name.lower()

                    if self.typing :
                        if event.key == pygame.K_RETURN:
                            if len(self.name) > 1:
                                self.waiting = True
                                self.typing = False
                        else:
                            self.typeName(key_name)

                    else:
                        self.typePassword(key_name)

            if self.check_button_pressed():
                print("----submit----")
                print("Name     : " + self.name)
                print("Password : " + self.password)


    def typeName(self, char):
        if char == "backspace":
            if len(self.name) > 0:
                self.name = self.name[:-1]
        elif char == "space":
            self.name += " "
        elif len(char) == 1:
            self.name += char
        if len(self.name) >= 20:
            self.name = self.name[:20]

    def typePassword(self, char):
        if char == "backspace":
            if len(self.password) > 0:
                self.password = self.password[:-1]
        elif char == "space":
            self.password += " "
        elif len(char) == 1:
            self.password += char
        if len(self.password) >= 20:
            self.password = self.password[:20]

    def check_button_pressed(self):
        return self.loginButton.get_clicked()


    def getName(self):
        return self.name

    def getPassword(self):
        return self.password

if __name__ == "__main__":
    pygame.font.init()
    main = MainMenu()
    main.run()

