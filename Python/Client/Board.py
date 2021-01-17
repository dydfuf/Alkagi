import pygame

class Board(object):

    def __init__(self, x, y):
        self.x = x
        self.y = y
        self.WIDTH = 720
        self.HEIGHT = 720
        self.compressed_board = []
        self.board = self.create_board()
        self.BORDER_THICKNESS = 5

    def draw(self, win):
        pygame.draw.rect(win, (0, 0, 0), (
        self.x - self.BORDER_THICKNESS / 2, self.y - self.BORDER_THICKNESS / 2, self.WIDTH + self.BORDER_THICKNESS,
        self.HEIGHT + self.BORDER_THICKNESS), self.BORDER_THICKNESS)
        for y, _ in enumerate(self.board):
            for x, col in enumerate(self.board[y]):
                pygame.draw.rect(win, col, (self.x + x * 8, self.y + y * 8, 8, 8), 0)