package com.adsquare.tictactoe.application.domain.services;


import com.adsquare.tictactoe.application.domain.exceptions.GridPositionNotEmpty;
import com.adsquare.tictactoe.application.domain.exceptions.GameAlreadyFinishedException;
import com.adsquare.tictactoe.application.domain.exceptions.PlayerAlreadyPlayedException;
import com.adsquare.tictactoe.application.domain.models.Game;
import com.adsquare.tictactoe.application.domain.models.Play;
import com.adsquare.tictactoe.application.domain.models.PlayerEnum;
import com.adsquare.tictactoe.application.domain.models.Position;
import com.adsquare.tictactoe.application.domain.services.validators.BoardValidator;
import com.adsquare.tictactoe.application.domain.services.validators.GameValidator;
import com.adsquare.tictactoe.application.domain.services.validators.PlayValidator;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GamePlayService {

    private final GameValidator gameValidator;
    private final BoardValidator boardValidator;
    private final PlayValidator playValidator;

    public Game playRound(@NonNull Game game, @NonNull PlayerEnum player, @NonNull Position position) {

        if (!gameValidator.gameIsValid(game)) {
            throw new GameAlreadyFinishedException(game.getId());
        }

        // 2. Check the board if the play position is already taken or the board is full
        if (!boardValidator.playIsValid(game.getBoard(), position)) {
            throw new GridPositionNotEmpty(position);
        }

        // 3. Check player turn
        if (!playValidator.playIsValid(game, player)) {
            throw new PlayerAlreadyPlayedException(player);
        }

        // 4. Update game state
        return updateGameState(game, player, position);
    }

    private Game updateGameState(@NonNull Game game, @NonNull PlayerEnum player, @NonNull Position position) {
        var lastPlay = new Play(player, position);

        game.setLastPlay(lastPlay);
        game.getBoard().add(lastPlay);

        if (playValidator.hasPlayerWon(game.getBoard(), player)) {
            game.setState(Game.State.WIN);
        } else if (!boardValidator.hasEmptySpaces(game.getBoard())) {
            game.setState(Game.State.FINISHED);
        }

        return game;
    }
}
