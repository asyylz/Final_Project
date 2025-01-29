package com.wgapp.worksheetgenerator.Utils;

import com.wgapp.worksheetgenerator.Models.Question;

public class PromtConstants {

    public static final String PROMPT_BEGINNING_COMPREHENSION = """
            Create a comprehension worksheet based on the provided passage. The worksheet should have exactly 5 questions, focusing on the following types specified by the user:
            Use the following format for the questions:
            1. [Question text] ([Question type]) ([Correct Option: X])
            2. [Question text] ([Question type]) ([Correct Option: X])
            
            Examples of question structures:
            - Question 1: What does the term "quagmire" mean in the passage? (VOCABULARY)(Correct Option: A)
              A.[Option Text]
              B.[Option Text]
              C.[Option Text]
              D.[Option Text]
            - Question 2: Why did Ferdinand Magellan offer to serve the future Emperor Charles V of Spain? (CHARACTER ANALYSIS)(Correct Option: B)
              A.[Option Text]
              B.[Option Text]
              C.[Option Text]
              D.[Option Text]
            
            Rules:
            1. Ensure each question is directly related to the passage.
            2. Maintain a balance between difficulty levels across the questions.
            3. Format the output as a numbered list.
            User Options:
            """;


    public static final String PROMPT_BEGINNING_CLOZE = """
                Create a cloze test worksheet based on the provided passage. The worksheet should have exactly 5 questions, with a focus on vocabulary, grammar, or context-based knowledge. Follow these instructions carefully:
           
                **Instructions for the passage:**
                - Rewrite the passage by replacing 5 key words or phrases with blanks (denoted as `________`).
                - Ensure the blanks test specific knowledge about word usage, grammar, or vocabulary in context.
            
                **Instructions for the questions:**
                - Each blank in the passage should correspond to a multiple-choice question.
                - The questions should ask for the best word or phrase to fit the blank, based on the context in the passage.
                - Each question should follow the structure below:
            
                **Question Format:**
                1. [Sentence with the blank as the question context] (Correct Option: [X])
                   A. [Option Text]
                   B. [Option Text]
                   C. [Option Text]
                   D. [Option Text]
                2. [Sentence with the blank as the question context]  (Correct Option: [X])
                   A. [Option Text]
                   B. [Option Text]
                   C. [Option Text]
                   D. [Option Text]
            
                **Examples of question structures:**
                 1.Magellan offered to prove that the East Indies fell under Spanish ________ (1). (Correct Option: A)
                 A. authority
                 B. exploration
                 C. discovery
                 D. negotiation
                
                 2.On September 20, 1519, he set sail from Spain with five ________ (2). (Correct Option: B)
                 A. maps
                 B. ships
                 C. men
                 D. weapons
            
                **Rules:**
                1. Each blank should test the user's ability to understand the passage and choose the correct word or phrase.
                2. Maintain a mix of easy, medium, and hard questions to test different levels of understanding.
            
                User Options:
            """;
    public static final String PROMPT_BEGINNING_VOCABULARY = """
                Create a vocabulary worksheet based on the provided passage in user option. The worksheet should have exactly 5 questions, following these instructions:
            
                **Instructions for the passage:**
                - AI should process the passage and  five key vocabulary  and  generate question asking synonymy, antonyms or definition.
                - These words should be selected based on their contextual importance.
                - Each question correct answer should be append at the end of the question as structured below.
                - Underline the words being asked in the question.

                **Question Format:**
                1. [Sentence with the blank as the question context] (Correct Option: [X])
                   A. [Option Text]
                   B. [Option Text]
                   C. [Option Text]
                   D. [Option Text]
                2. [Sentence with the blank as the question context] (Correct Option: [X])
                   A. [Option Text]
                   B. [Option Text]
                   C. [Option Text]
                   D. [Option Text]
       
                **Examples of question structures:**
                1. What is the antonym of  crucial in this sentence "The scientist made a crucial discovery that changed history." (Correct Option: A)
                   A. gentle
                   B. aggressive
                   C. mild
                   D. slow
                2. What is the definition of ferocious  in this sentence "The storm was so ferocious that it uprooted several trees." (Correct Option: A)
                   A. gentle
                   B. aggressive
                   C. mild
                   D. slow
            
                **Rules:**
                1. Ensure each question is directly linked to the passage.
            
                User Options:
            """;
    public static final String PROMPT_BEGINNING_SPAG = """
                Create spelling, punctuation and grammar worksheet based on the provided passage in user option. The worksheet should have exactly 5 questions, following these instructions:
            
                **Instructions for the passage:**
                - AI should process the passage and  generate question asking related to spelling, punctuation and grammar contextual importance.
                - Each question correct answer should be append at the end of the question as structured below.
                - Do not refer passage in the questions.
                 ** wrong **
                 In the passage, which punctuation mark is missing in the sentence:
                 "Those long days at sea many of Magellan’s men died of starvation and disease". (Correct Option: B)
 
                 ** correct **
                 In the following sentence, which punctuation mark is missing in the sentence:
                 "Those long days at sea many of Magellan’s men died of starvation and disease". (Correct Option: A)

                 **Question Format:**
                1. [Sentence  with the question context] (Correct Option: [X])
                   A. [Option Text]
                   B. [Option Text]
                   C. [Option Text]
                   D. [Option Text]
                2. [Sentence with the question context] (Correct Option: [X])
                   A. [Option Text]
                   B. [Option Text]
                   C. [Option Text]
                   D. [Option Text]
       
                **Examples of question structures:**
                1. "On September 20, 1519, Magellan set sail from Spain with five ships. More than a year later,
                one of these ships was explorg the topography of South America in search of a water route across
                the continent." Which word spelt wrong in the sentence? (Correct Option: A)
                   A. Exploring
                   B. continent
                   C. September
                   D. Spain
            
                2. "On September 20___ 1519__ Magellan set sail from Spain with five ships__ More than a year later__
                one of these ships was exploring the topography of South America in search of a water route across the
                continent__" What is the missing punctuation in the sentence? (Correct Option: B)
                   A. Comma, Semi colon, Full stop, Comma, Full stop,
                   B. Comma, Comma, Full stop, Dash, Full stop,
                   C. Comma, Comma, Full stop, Comma, Full stop,
                   D. Comma, Colon, Full stop, Comma, Full stop,
            
                **Rules:**
                1. Ensure each question is directly linked to the passage.
            
                User Options:
            """;

}
