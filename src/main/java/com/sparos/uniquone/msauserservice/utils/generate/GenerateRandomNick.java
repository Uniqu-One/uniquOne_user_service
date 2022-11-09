package com.sparos.uniquone.msauserservice.utils.generate;

import com.sparos.uniquone.msauserservice.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class GenerateRandomNick {

    private final UserRepository userRepository;

    private static List<String> firstWords = Arrays.asList("즐거운", "비틀비틀", "헤롱헤롱", "침흘리는", "코흘리는", "도도한", "빡빡머리의", "슬픈눈의", "맑은눈의", "행복한", "잠을못잔", "예쁜", "화난", "귀여운", "배고픈"
            , "철학적인", "현학적인", "슬픈", "푸른", "비싼", "밝은", "심드렁한", "바보같은", "멍청한"
            , "똑똑한", "배긁는", "안경쓴", "눈이침침한", "백발의", "기분좋은");
    private static List<String> secondWords = Arrays.asList("고라니", "호랑이", "개구리", "푸들", "포메라니안", "설인", "유니콘", "청룡", "백호", "주작", "현무", "구미호", "인면조", "봉황", "불가사리", "이무기", "왁왁", "구울"
            , "고블린", "악마", "엘프", "흡혈귀", "좀비", "사마귀", "사탄", "무당벌레", "달팽이", "불가사리", "진드기", "호호새", "돌고래", "해파리", "두두", "거북이", "독수리", "참새", "백조");

    private String shuffle() {
        Collections.shuffle(firstWords);
        Collections.shuffle(secondWords);

        return firstWords.get(0) + "_" + secondWords.get(0);
    }


    public String generate() {
        //닉네임 생성 해주는애 경우의 수가 낮아서 해주는 조치.
        String nickname = shuffle();

        int checkCnt = 0;
        while (userRepository.existsByNickname(nickname)) {

            nickname = shuffle();
            if (checkCnt >= 5) {
                nickname = UUID.randomUUID().toString();
            }
            checkCnt++;
        }
        return nickname;
    }
}
